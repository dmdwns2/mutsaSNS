//package com.example.finalproject.adapter;
//
//import com.example.finalproject.domain.Post;
//import com.example.finalproject.repository.JpaPostRepository;
//import com.example.finalproject.service.PostGetService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class PostGetJpaRepositoryAdapter implements PostGetService.PostGetRepository {
//    private final JpaPostRepository repository;
//
//    @Override
//    public Post find(long id) {
//        return repository.findById(id)
//                         .map(postEntity -> {
//                             return new Post(
//                                     postEntity.getId(),
//                                     postEntity.getTitle(),
//                                     postEntity.getBody(),
//                                     postEntity.getUserName()
//                             );
//                         })
//                         .orElseThrow(() -> {
//                             return new IllegalArgumentException("post does not exist");
//                         });
//    }
//}
