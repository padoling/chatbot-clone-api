package hellobot.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hellobot.api.IntegrationTest;
import hellobot.api.domain.image.Image;
import hellobot.api.domain.image.ImageRepository;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
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

import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentRequest;
import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SessionControllerTest extends IntegrationTest {

    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        ScenarioPostRequestDto scenarioPostRequestDto = (ScenarioPostRequestDto)jsonToObject("classpath:test_mock/scenario_post_request.json", new TypeReference<ScenarioPostRequestDto>(){});
        scenarioService.saveScenario(scenarioPostRequestDto);
        imageRepository.save(Image.builder()
                .id("imageid1")
                .imageUrl("image/1604940118574_lamama.png")
                .build());
    }

    @AfterEach
    public void cleanUp() {
        scenarioRepository.deleteAll();
        imageRepository.deleteAll();
    }

    @Test
    public void startSession_success() throws Exception {
        // given
        Scenario scenario = scenarioRepository.findAll().get(0);
        SessionDto sessionDto = SessionDto.builder()
                .userId("userId123")
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
                                fieldWithPath("id").ignored(),
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 id"),
                                fieldWithPath("scenarioId").type(JsonFieldType.STRING).description("시나리오 id"),
                                fieldWithPath("variables").ignored(),
                                fieldWithPath("messageNumber").type(JsonFieldType.NUMBER).description("처음 시작할 메시지 번호")
                        ),
                        responseFields(
                                fieldWithPath("messageContent").type(JsonFieldType.STRING).description("처음 표시될 메시지 내용"),
                                fieldWithPath("imageUrlList").type(JsonFieldType.ARRAY).description("메시지에 들어갈 이미지의 url 리스트"),
                                fieldWithPath("inputType").type(JsonFieldType.STRING).description("메시지 이후 받을 수 있는 응답 유형"),
                                fieldWithPath("inputContents").type(JsonFieldType.ARRAY).description("메시지 이후 받을 수 있는 응답 내용"),
                                fieldWithPath("nextMessageNums").type(JsonFieldType.ARRAY).description("응답에서 이어질 수 있는 메시지 번호")
                        )
                ));
    }


}
