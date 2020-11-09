package hellobot.api.controller;

import hellobot.api.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentRequest;
import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScenarioControllerTest extends IntegrationTest {

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
                                fieldWithPath("messageDtoList[].nextInputNum").type(JsonFieldType.NUMBER).description("다음 번 사용자 인풋 순서"),
                                fieldWithPath("inputDtoList[].inputType").type(JsonFieldType.STRING).description("사용자 인풋 유형(TEXT, OPTION, TAROT)"),
                                fieldWithPath("inputDtoList[].contents").type(JsonFieldType.ARRAY).description("사용자 인풋 내용"),
                                fieldWithPath("inputDtoList[].number").type(JsonFieldType.NUMBER).description("인풋 순서"),
                                fieldWithPath("inputDtoList[].nextMessageNums").type(JsonFieldType.ARRAY).description("다음 번 메시지 순서")
                        ),
                        responseFields(
                                fieldWithPath("scenarioId").type(JsonFieldType.STRING).description("생성된 시나리오 id")
                        )
                ));
    }
}