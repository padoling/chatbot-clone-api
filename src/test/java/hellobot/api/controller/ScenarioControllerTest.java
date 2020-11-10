package hellobot.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScenarioControllerTest extends IntegrationTest {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ScenarioService scenarioService;

    @BeforeEach
    public void setUp() {
        List<Scenario> scenarioList = (List<Scenario>) jsonToObject("classpath:test_mock/scenario_list.json", new TypeReference<List<Scenario>>(){});
        scenarioList.stream().forEach(
                scenario -> scenarioRepository.save(scenario)
        );
        userRepository.save(User.builder()
                .id("userId")
                .name("박세림")
                .userRole(UserRole.ADMIN)
                .build());
        ScenarioPostRequestDto scenarioPostRequestDto = (ScenarioPostRequestDto)jsonToObject("classpath:test_mock/scenario_post_example.json", new TypeReference<ScenarioPostRequestDto>(){});
        String scenarioId = scenarioService.saveScenario(scenarioPostRequestDto);
        Map<String, String> variables = new HashMap<>();
        variables.put("userName", "박세림");
        sessionRepository.save(Session.builder()
                .userId("userId")
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
        sessionRepository.deleteAll();
        imageRepository.deleteAll();
    }

    @Test
    public void postScenario_success() throws Exception {
        // given
        String request = jsonToString("classpath:test_mock/scenario_post_request.json");

        // when
        ResultActions resultActions = mvc.perform(
                post("/scenario")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request)
        );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("scenario-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("시나리오 이름"),
                                fieldWithPath("variables").type(JsonFieldType.ARRAY).description("시나리오에 사용될 변수 리스트"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("시나리오에 대한 설명"),
                                fieldWithPath("messageDtoList[].contents").type(JsonFieldType.STRING).description("챗봇에서 보내는 메시지 내용"),
                                fieldWithPath("messageDtoList[].imageList").type(JsonFieldType.ARRAY).description("챗봇 메시지에 사용될 이미지 id 리스트").optional(),
                                fieldWithPath("messageDtoList[].number").type(JsonFieldType.NUMBER).description("메시지 순서"),
                                fieldWithPath("messageDtoList[].nextInputNum").type(JsonFieldType.NUMBER).description("다음 번 사용자 응답 순서"),
                                fieldWithPath("inputDtoList[].inputType").type(JsonFieldType.STRING).description("사용자 응답 유형(TEXT, OPTION, TAROT)"),
                                fieldWithPath("inputDtoList[].contents").type(JsonFieldType.ARRAY).description("사용자 응답 내용"),
                                fieldWithPath("inputDtoList[].number").type(JsonFieldType.NUMBER).description("응답 순서"),
                                fieldWithPath("inputDtoList[].nextMessageNums").type(JsonFieldType.ARRAY).description("다음 번 메시지 순서")
                        ),
                        responseFields(
                                fieldWithPath("scenarioId").type(JsonFieldType.STRING).description("생성된 시나리오 id")
                        )
                ));
    }

    @Test
    public void getScenarioList_success() throws Exception {
        // given
        String size = "2";
        String page = "0";

        // when
        ResultActions resultActions = mvc.perform(
                get("/scenario")
                .param("size", size)
                .param("page", page)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("scenario-list-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("size").description("페이지 사이즈"),
                                parameterWithName("page").description("페이지 번호")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.STRING).description("시나리오 id"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("시나리오 이름"),
                                fieldWithPath("[].variables").type(JsonFieldType.ARRAY).description("시나리오에 사용될 변수 리스트").optional(),
                                fieldWithPath("[].description").type(JsonFieldType.STRING).description("시나리오에 대한 설명")
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
                get("/scenario/{scenarioId}", scenarioId)
                .param("userId", userId)
                .param("inputType", inputType.toString())
                .param("contentMap", String.valueOf(contentMap))
                .param("nextMessageNum", String.valueOf(nextMessageNum))
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("scenario-get-next-message",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("scenarioId").description("시나리오 id")
                        ),
                        requestParameters(
                                parameterWithName("userId").description("유저 id"),
                                parameterWithName("inputType").description("유저의 응답 유형"),
                                parameterWithName("contentMap").description("inputType이 TEXT나 TAROT인 경우, user의 응답 내용").optional(),
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