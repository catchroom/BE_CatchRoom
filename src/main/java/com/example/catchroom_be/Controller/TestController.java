package com.example.catchroom_be.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
public class TestController {

    @PostMapping("/do")
    public String Test(@RequestParam String name) {
        return "나는 " + name + "입니다.";
    }
}
