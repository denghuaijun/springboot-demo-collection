package com.dhj.demo.business.springconfig;


import com.dhj.demo.business.common.utils.redis.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

//@Configuration
@Slf4j
@Data
public class JedisConfiguration {
    @Value("${redis.online}")
    private Boolean online;

    @Value("${redis.ips}")
    private String ips;

    @Value("${redis.maxredirections}")
    private int maxredirections;

    @Value("${redis.timeout}")
    private int timeout;


    @Value("${redis.maxIdle}")
    private int maxTotal;

    @Value("${redis.maxIdle}")
    private int maxIdle;


    @Value("${redis.minIdle}")
    private int minIdle;


    @Value("${redis.maxWait}")
    private int maxWait;

    @Value("${redis.testOnBorrow}")
    private Boolean testOnBorrow;


    @Value("${redis.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${redis.prefix}")
    private String prefix;

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = RedisManager.Builder()
                                    .hostAndPort(ips).maxTotal(maxTotal)
                .maxIdle(maxIdle).minIdle(minIdle).maxWaitMillis(maxWait)
                .testOnBorrow(testOnBorrow).testWhileIdle(testWhileIdle).prefix(prefix).poolBuild();
        return redisManager;
    }

    @Bean
    public RedisString redisString(RedisManager redisManager){
        return redisManager.redisStringWapper();
    }

    @Bean
    public RedisHash redisHash(RedisManager redisManager){
        return redisManager.redisHashWapper();
    }

    @Bean
    public RedisArray redisArray(RedisManager redisManager){
        return redisManager.redisArrayWapper();
    }

    @Bean
    public RedisKey redisKey(RedisManager redisManager){return redisManager.redisKeyWapper();}
}
