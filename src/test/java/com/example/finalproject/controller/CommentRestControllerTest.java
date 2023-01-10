//package com.example.finalproject.controller;
//
//import com.example.finalproject.domain.dto.request.CommentAddRequest;
//import com.example.finalproject.domain.dto.response.CommentAddResponse;
//import com.example.finalproject.repository.AlarmRepository;
//import com.example.finalproject.repository.CommentRepository;
//import com.example.finalproject.repository.PostRepository;
//import com.example.finalproject.repository.UserRepository;
//import com.example.finalproject.service.CommentService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//class CommentRestControllerTest {
//
//    @MockBean
//    CommentRepository commentRepository;
//
//    @MockBean
//    PostRepository postRepository;
//
//    @MockBean
//    UserRepository userRepository;
//
//    @MockBean
//    AlarmRepository alarmRepository;
//
//
//    CommentService service = new CommentService(commentRepository, postRepository, userRepository, alarmRepository);
//
//    private final String USERNAME = "messi";
//    private final Long ID = 1L;
//
//    @Test
//    void 댓글_작성_성공() throws Exception {
//        //given
//        CommentAddRequest request = new CommentAddRequest("테스트");
//        //when
//        CommentAddResponse response = service.add(request, USERNAME, ID);
//        String comment = response.getComment();
//        //then
//        Assertions.assertThat(comment).isEqualTo("테스트");
//    }
//
//}