package hellobot.api.controller;

import hellobot.api.IntegrationTest;
import hellobot.api.domain.tarot.Tarot;
import hellobot.api.domain.tarot.TarotRepository;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TarotControllerTest extends IntegrationTest {

    @Autowired
    private TarotRepository tarotRepository;

    @BeforeEach
    public void setUp() {
        tarotRepository.save(Tarot.builder()
                .scenarioId("scenarioId")
                .tarotNum(15)
                .description("악마 카드네...\n아주 매력적인 사람이 곧 나타날텐데\n지금은 그냥 지나가는 게 좋겠어")
                .build());
    }

    @AfterEach
    public void cleanUp() {
        tarotRepository.deleteAll();
    }

    @Test
    public void postTarot_success() throws Exception {
        // given
        String request = jsonToString("classpath:test_mock/tarot_post_request.json");

        // when
        ResultActions resultActions = mvc.perform(
                post("/tarot")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request)
        );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("tarot-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("scenarioId").type(JsonFieldType.STRING).description("시나리오 id"),
                                fieldWithPath("tarotNum").type(JsonFieldType.NUMBER).description("타로카드 번호"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("시나리오에 따른 타로카드 해석 메시지")
                        ),
                        responseFields(
                                fieldWithPath("tarotId").type(JsonFieldType.STRING).description("타로 id")
                        )
                ));
    }

    @Test
    public void getTarotList_success() throws Exception {
        // given
        String size = "2";
        String page = "0";

        // when
        ResultActions resultActions = mvc.perform(
                get("/tarot")
                .param("size", size)
                .param("page", page)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("tarot-list-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("size").description("페이지 사이즈"),
                                parameterWithName("page").description("페이지 번호")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.STRING).description("타로 id"),
                                fieldWithPath("[].scenarioId").type(JsonFieldType.STRING).description("시나리오 id"),
                                fieldWithPath("[].tarotNum").type(JsonFieldType.NUMBER).description("타로카드 번호"),
                                fieldWithPath("[].description").type(JsonFieldType.STRING).description("시나리오에 따른 타로카드 해석 메시지")
                        )
                ));
    }
}
