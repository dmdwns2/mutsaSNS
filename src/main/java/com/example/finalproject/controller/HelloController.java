package com.example.finalproject.controller;

import com.example.finalproject.service.HelloService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
@AllArgsConstructor
public class HelloController {

    private final HelloService service;

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok().body("김응준");
    }

    @GetMapping("/{num}")
    public ResponseEntity<String> num(@PathVariable Integer num){
        return ResponseEntity.ok().body(service.sumOfNum(num).toString());
    }
}

