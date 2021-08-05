package com.ssm.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Bean
    public TransactionAwareCacheManagerProxy cacheManager(CacheManager targetCacheManager) {
        return new TransactionAwareCacheManagerProxy(targetCacheManager);
    }

    @Bean
    public CacheManager targetCacheManager() {
        return new CaffeineCacheManager();
    }
}
