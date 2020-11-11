package hellobot.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hellobot.api.IntegrationTest;
import hellobot.api.domain.user.UserRepository;
import hellobot.api.domain.user.UserRole;
import hellobot.api.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
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

public class UserControllerTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void postTestUser_success() throws Exception {
        // given
        UserDto userDto = UserDto.builder()
                .name("박세림")
                .userRole(UserRole.ADMIN)
                .build();

        // when
        ResultActions resultActions = mvc.perform(
                post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("test-user-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userRole").type(JsonFieldType.STRING).description("유저 역할(USER, ADMIN)"),
                                fieldWithPath("id").ignored()
                        ),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 id")
                        )
                ));
    }
}
