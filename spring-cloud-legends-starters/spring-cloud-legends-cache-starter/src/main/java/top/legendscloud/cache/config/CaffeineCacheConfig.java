package top.legendscloud.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import top.legendscloud.cache.config.properties.CacheRedisCaffeineProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author herion
 * @Description //TODO
 * @Date  2019/5/22
 * @Param
 * @return
 **/
@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    public static final int DEFAULT_MAXSIZE = 50000;
    public static final int DEFAULT_TTL = 10;


    @Autowired
    CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

    /**
     * 创建基于Caffeine的Cache Manager
     * @return
     */
    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager=new CaffeineCacheManager();
        return cacheManager;
    }


    /**
     * @Author herion
     * @Description CaffeineCacheManager 有效期默认60秒
     * @Date 2019/5/23
     * @Param []
     * @return org.springframework.cache.CacheManager
     **/
    @Bean("caffeineExpireCacheManager")
    public CacheManager caffeineExpireCacheManager() {
        CaffeineCacheManager cacheManager=new CaffeineCacheManager();
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder().expireAfterWrite(60,TimeUnit.SECONDS);
        cacheManager.setCaffeine(cacheBuilder);
        return cacheManager;
    }
}