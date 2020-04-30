package ru.yourapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachingConfig.class);

    private static final long DEFAULT_CACHE_EXPIRATION = 3600;

    @Value("${redis.hostName}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
        redisConf.setHostName(host);
        redisConf.setPort(port);
        return new JedisConnectionFactory(redisConf);
    }

    @Bean
    public RedisCacheManager cacheManager() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues().entryTtl(Duration.ofSeconds(DEFAULT_CACHE_EXPIRATION))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        defaultCacheConfiguration.usePrefix();
        cacheConfigurations.put("api-data", defaultCacheConfiguration.entryTtl(Duration.ofSeconds(30)));
        cacheConfigurations.put("subscription-data", defaultCacheConfiguration.entryTtl(Duration.ofHours(30)));
        return RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(defaultCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                LOGGER.error("cache : {} , key : {}", cache, key);
                LOGGER.error("handleCacheGetError", exception);
                super.handleCacheGetError(exception, cache, key);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                LOGGER.error("cache : {} , key : {} , value : {} ", cache, key, value);
                LOGGER.error("handleCachePutError", exception);
                super.handleCachePutError(exception, cache, key, value);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                LOGGER.error("cache : {} , key : {}", cache, key);
                LOGGER.error("handleCacheEvictError", exception);
                super.handleCacheEvictError(exception, cache, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                LOGGER.error("cache : {} ", cache);
                LOGGER.error("handleCacheClearError", exception);
                super.handleCacheClearError(exception, cache);
            }
        };
    }
}