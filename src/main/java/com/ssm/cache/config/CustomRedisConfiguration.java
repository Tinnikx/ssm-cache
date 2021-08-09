package com.ssm.cache.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("redis")
public class CustomRedisConfiguration {

    @Bean
    public CacheManager targetCacheManager(LettuceConnectionFactory connectionFactory,
                                           Map<String, RedisCacheConfiguration> redisCacheConfigurationMap) {
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(connectionFactory)
            //配置默认配置, 比如默认的超时时间
            .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(3L)))
            //配置其他的单独的cache配置, 比如现在有个cache存储空间名为test, 现在不想使用默认配置, 那么在这里设置
            .withInitialCacheConfigurations(redisCacheConfigurationMap)
            .build();
    }

    @Bean
    public LettuceConnectionFactory connectionFactory(GenericObjectPoolConfig poolConfig) {
        //单机Redis配置信息, 哨兵模式配置类为RedisSentinelConfiguration, 集群模式配置类为RedisClusterConfiguration
        RedisConfiguration redisConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        //连接池配置
        LettucePoolingClientConfiguration poolConfiguration = LettucePoolingClientConfiguration.builder()
            .commandTimeout(Duration.ofSeconds(20)).poolConfig(poolConfig).build();
        return new LettuceConnectionFactory(redisConfiguration, poolConfiguration);
    }

    @Bean
    public GenericObjectPoolConfig poolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(2000);
        poolConfig.setMinIdle(100);
        poolConfig.setMaxTotal(2000);
        poolConfig.setMaxWaitMillis(10000);
        return poolConfig;
    }

    @Bean
    public Map<String, RedisCacheConfiguration> redisCacheConfigurationMap() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        configurationMap.put("test", config.entryTtl(Duration.ofSeconds(20L)));
        return configurationMap;
    }
}
