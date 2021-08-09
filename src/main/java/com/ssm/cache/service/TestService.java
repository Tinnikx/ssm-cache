package com.ssm.cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Cacheable(cacheNames = "test", key = "#id")
    public String test(String id) {
        return id;
    }

    public String test2() {
        return "success";
    }
}
