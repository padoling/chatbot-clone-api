package hellobot.api.controller;

import hellobot.api.IntegrationTest;
import hellobot.api.domain.image.Image;
import hellobot.api.domain.image.ImageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentRequest;
import static hellobot.api.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest extends IntegrationTest {

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    public void setUp() {
        imageRepository.save(Image.builder()
                .name("lamama")
                .imageUrl("image/1604940118574_lamama.png")
                .build()
        );
    }

    @AfterEach
    public void cleanUp() {
        imageRepository.deleteAll();
    }

    @Test
    public void postImage_success() throws Exception {
        // given
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "meow_party.gif",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "meow_party.gif".getBytes());

        // when
        ResultActions resultActions = mvc.perform(
                fileUpload("/image")
                .file(imageFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", "meow")
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("image-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestPartBody("imageFile"),
                        requestParameters(
                                parameterWithName("name").description("이미지 이름")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("이미지 id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이미지 이름"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("이미지에 접근 가능한 url")
                        )
                ));
    }

    @Test
    public void getImageList_success() throws Exception {
        // given
        String size = "2";
        String page = "0";

        // when
        ResultActions resultActions = mvc.perform(
                get("/image")
                .param("size", size)
                .param("page", page)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("image-list-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("size").description("페이지 사이즈"),
                                parameterWithName("page").description("페이지 번호")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.STRING).description("이미지 id"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("이미지 이름"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("이미지에 접근 가능한 url")
                        )
                ));
    }
}
