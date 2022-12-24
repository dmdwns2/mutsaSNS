package com.example.finalproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostAddServiceTest {

    private static final String TITLE = "title";
    private static final String BODDY = "body";
    private static final String USERNAME = "userName";

    JpaRepository repository;

    @Test
    public void add_one_post(){
        PostAddRepositoryStub repositoryStub = new PostAddRepositoryStub();
        PostAddService service = new PostAddService(repositoryStub);
        service.add(new PostAddService.PostAddCommand(TITLE,BODDY));
        assertEquals(TITLE, repositoryStub.getLastTitle());
        assertEquals(BODDY, repositoryStub.getLastBody());
        assertEquals(USERNAME, repositoryStub.getLastUserName());
    }


    static class PostAddRepositoryStub implements PostAddService.PostAddRepository{

        private String lastTitle;
        private String lastBody;
        private String lastUserName;

        @Override
        public long add(String title, String body) {
            lastTitle = title;
            lastBody = body;
            return 0;
        }

        public String getLastTitle() {
            return lastTitle;
        }

        public String getLastBody() {
            return lastBody;
        }

        public String getLastUserName() {
            return lastUserName;
        }
    }



    // 비어있을 때
    @Test
    public void add_post_with_empty_title(){
        assertThrows(IllegalArgumentException.class, () -> {
            PostAddService service = new PostAddService(new EmptyRepositoryStub());
            service.add(new PostAddService.PostAddCommand("", "body"));
        });
    }

    static class EmptyRepositoryStub implements PostAddService.PostAddRepository {
        @Override
        public long add(String title, String body) {
            return 0;
        }
    }

    @Test
    public void add_post_with_empty_body(){
        assertThrows(IllegalArgumentException.class, () -> {
            PostAddService service = new PostAddService(new EmptyRepositoryStub());
            service.add(new PostAddService.PostAddCommand("title", ""));
        });
    }
}