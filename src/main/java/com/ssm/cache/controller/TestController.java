package com.ssm.cache.controller;

import com.ssm.cache.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/test")
    public String test(String id) {
        return testService.test(id);
    }

    @GetMapping(value = "/test2")
    public String test2() {
        return testService.test2();
    }
}
