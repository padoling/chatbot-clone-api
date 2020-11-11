package hellobot.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hellobot.api.IntegrationTest;
import hellobot.api.domain.image.Image;
import hellobot.api.domain.image.ImageRepository;
import hellobot.api.domain.input.InputType;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.domain.session.Session;
import hellobot.api.domain.session.SessionRepository;
import hellobot.api.domain.user.User;
import hellobot.api.domain.user.UserRepository;
import hellobot.api.domain.user.UserRole;
import hellobot.api.dto.ScenarioPostRequestDto;
import hellobot.api.dto.SessionDto;
import hellobot.api.service.ScenarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentRequest;
import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SessionControllerTest extends IntegrationTest {

    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        ScenarioPostRequestDto scenarioPostRequestDto = (ScenarioPostRequestDto)jsonToObject("classpath:test_mock/scenario_post_request.json", new TypeReference<ScenarioPostRequestDto>(){});
        String scenarioId = scenarioService.saveScenario(scenarioPostRequestDto);
        User user = userRepository.save(User.builder()
                .id("userId")
                .name("박세림")
                .userRole(UserRole.ADMIN)
                .build());

        Map<String, String> variables = new HashMap<>();
        variables.put("userName", user.getName());
        sessionRepository.save(Session.builder()
                .userId(user.getId())
                .scenarioId(scenarioId)
                .variables(variables)
                .messageNumber(1)
                .build());
        List<Image> imageList = (List<Image>) jsonToObject("classpath:test_mock/image_post_example.json", new TypeReference<List<Image>>(){});
        imageRepository.saveAll(imageList);
    }

    @AfterEach
    public void cleanUp() {
        scenarioRepository.deleteAll();
        userRepository.deleteAll();
        imageRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @Test
    public void startSession_success() throws Exception {
        // given
        Scenario scenario = scenarioRepository.findAll().get(0);
        User user = userRepository.save(User.builder()
                .name("tester")
                .userRole(UserRole.USER)
                .build()
        );
        SessionDto sessionDto = SessionDto.builder()
                .userId(user.getId())
                .scenarioId(scenario.getId())
                .messageNumber(1)
                .build();

        // when
        ResultActions resultActions = mvc.perform(
                post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto))
        );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("session-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("세션 id(포함해서 보낼 경우 해당 세션을 업데이트함)").optional(),
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 id"),
                                fieldWithPath("scenarioId").type(JsonFieldType.STRING).description("시나리오 id"),
                                fieldWithPath("variables").ignored(),
                                fieldWithPath("messageNumber").type(JsonFieldType.NUMBER).description("처음 시작할 메시지 번호")
                        ),
                        responseFields(
                                fieldWithPath("messageContent").type(JsonFieldType.STRING).description("세션이 시작되며 맨 처음 표시될 메시지 내용"),
                                fieldWithPath("imageUrlList").type(JsonFieldType.ARRAY).description("메시지에 들어갈 이미지의 url 리스트"),
                                fieldWithPath("inputType").type(JsonFieldType.STRING).description("메시지 이후 받을 수 있는 응답 유형"),
                                fieldWithPath("inputContents").type(JsonFieldType.ARRAY).description("메시지 이후 받을 수 있는 응답 내용"),
                                fieldWithPath("nextMessageNums").type(JsonFieldType.ARRAY).description("유저 응답에 따라 매칭될 다음 번 메시지 번호")
                        )
                ));
    }

    @Test
    public void getSessionByUserId_success() throws Exception {
        // given
        String userId = "userId";

        // when
        ResultActions resultActions = mvc.perform(
                get("/session/user/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("session-get-by-userid",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("userId").description("유저 id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("세션 id"),
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 id"),
                                fieldWithPath("scenarioId").type(JsonFieldType.STRING).description("시나리오 id"),
                                fieldWithPath("variables.*").type(JsonFieldType.STRING).description("시나리오에서 사용될 변수들"),
                                fieldWithPath("messageNumber").type(JsonFieldType.NUMBER).description("처음 시작할 메시지 번호")
                        )
                ));
    }

    @Test
    public void finishSession_success() throws Exception {
        // given
        Session session = sessionRepository.findAll().get(0);

        // when
        ResultActions resultActions = mvc.perform(
                delete("/session/{sessionId}", session.getId())
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isNoContent())
                .andDo(document("session-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("sessionId").description("삭제할 세션 id")
                        )
                ));
    }

    @Test
    public void getNextMessage_success() throws Exception {
        // given
        String userId = "userId";
        InputType inputType = InputType.OPTION;
        int nextMessageNum = 3;
        Session session = sessionRepository.findByUserId(userId).orElse(null);
        String scenarioId = session.getScenarioId();
        Map<String, String> contentMap = new HashMap<>();

        // when
        ResultActions resultActions = mvc.perform(
                get("/session/message")
                        .param("scenarioId", scenarioId)
                        .param("userId", userId)
                        .param("inputType", inputType.toString())
                        .param("contents", String.valueOf(contentMap))
                        .param("nextMessageNum", String.valueOf(nextMessageNum))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("session-get-next-message",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("scenarioId").description("시나리오 id"),
                                parameterWithName("userId").description("유저 id"),
                                parameterWithName("inputType").description("유저의 응답 유형"),
                                parameterWithName("contents").description("inputType이 TEXT나 TAROT인 경우, user의 응답 내용(url encoded json)").optional(),
                                parameterWithName("nextMessageNum").description("다음 순서의 메시지 번호")
                        ),
                        responseFields(
                                fieldWithPath("messageContent").type(JsonFieldType.STRING).description("보여질 메시지 내용"),
                                fieldWithPath("imageUrlList").type(JsonFieldType.ARRAY).description("메시지 내용에 들어갈 이미지 url 리스트").optional(),
                                fieldWithPath("inputType").type(JsonFieldType.STRING).description("메시지 이후 받을 수 있는 응답 유형"),
                                fieldWithPath("inputContents").type(JsonFieldType.ARRAY).description("메시지 이후 받을 수 있는 응답 내용").optional(),
                                fieldWithPath("nextMessageNums").type(JsonFieldType.ARRAY).description("유저 응답에 따라 매칭될 다음 번 메시지 번호").optional()
                        )
                ));
    }
}
