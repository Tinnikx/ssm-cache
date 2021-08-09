package com.ssm.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!redis")
public class CaffeineCacheConfiguration {

    @Bean
    public CacheManager targetCacheManager() {
        return new CaffeineCacheManager();
    }
}
