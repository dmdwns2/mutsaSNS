package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostAddRequest;
import com.example.finalproject.domain.dto.PostAddResponse;
import com.example.finalproject.service.PostService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostRestControllerTest {

    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String USERNAME = "messi";


    private final PostService service;

    public PostRestControllerTest(PostService service) {
        this.service = service;
    }



    // GET /posts/1 로 조회시
    //조회 성공 - id, title, body, userName 4가지 항목이 있는지 검증
    @Test
    void ID조회_확인() {

    }

    @Test
    void ADD_동작_확인()  {
        PostAddResponse addResult = service.add(new PostAddRequest(TITLE, BODY), USERNAME);
        assertEquals(TITLE, addResult.getTitle());
        assertEquals(BODY, addResult.getBody());
    }

    @Test
    void 작성_인증_실패_JWT를_Bearer_Token으로_보내지_않은_경우() {
    }

    @Test
    void 작성_인증_실패_JWT가_유효하지_않은_경우() {
    }

    // 포스트 수정
    @Test
    void 수정_인증_실패() {
    }

    @Test
    void 수정_작성자_불일치() {
    }

    @Test
    void 수정_데이터베이스_에러() {
    }

    @Test
    void 수정_성공() {
    }

    // 포스트 삭제
    @Test
    void 삭제_인증_실패() {
    }

    @Test
    void 삭제_작성자_불일치() {
    }

    @Test
    void 삭제_데이터베이스_에러() {
    }

    @Test
    void 삭제_성공() {
    }

    // 포스트 리스트
    @Test
    void 조회_성공_0번이_1번보다_날짜가_최신() {
    }
}
