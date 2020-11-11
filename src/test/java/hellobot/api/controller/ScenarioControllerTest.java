package hellobot.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hellobot.api.IntegrationTest;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentRequest;
import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScenarioControllerTest extends IntegrationTest {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @BeforeEach
    public void setUp() {
        List<Scenario> scenarioList = (List<Scenario>) jsonToObject("classpath:test_mock/scenario_list.json", new TypeReference<List<Scenario>>(){});
        scenarioList.stream().forEach(
                scenario -> scenarioRepository.save(scenario)
        );
    }

    @AfterEach
    public void cleanUp() {
        scenarioRepository.deleteAll();
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
}