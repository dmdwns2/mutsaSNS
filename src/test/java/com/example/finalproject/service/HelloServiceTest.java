package com.example.finalproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloServiceTest {

        // Spring을 안쓰고 테스트 하기 때문에 new를 이용해 초기화를 해줍니다.
        // Pojo방식을 최대한 활용합니다.
        HelloService service = new HelloService();

        @Test
        @DisplayName("자릿수 합 잘 구하는지")
        void sumOfDigit() {
            assertEquals(21, service.sumOfNum(687));
            assertEquals(22, service.sumOfNum(787));
            assertEquals(0, service.sumOfNum(0));
            assertEquals(5, service.sumOfNum(11111));
        }
}