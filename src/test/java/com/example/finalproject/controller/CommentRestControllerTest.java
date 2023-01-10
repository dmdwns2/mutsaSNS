package com.example.finalproject.controller;

import com.example.finalproject.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class CommentRestControllerTest {

    @MockBean
    CommentService service;

    private final String USERNAME = "messi";
    private final Long ID = 1L;

    @Test
    void 댓글_작성_성공() throws Exception {
//        //given
//        CommentAddRequest request = new CommentAddRequest("테스트");
//        //when
//        CommentAddResponse response = service.add(request, USERNAME, ID);
//        String comment = response.getComment();
//        //then
//        Assertions.assertThat(comment).isEqualTo("테스트");
    }

}