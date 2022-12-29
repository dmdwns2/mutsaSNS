package com.example.finalproject.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public Integer sumOfNum(Integer num) {
        Integer sum = 0;

        while(num != 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }
}
