package com.legends.cloud.db.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.legends.cloud.db.annotation.MapperScanner;
import com.legends.cloud.db.config.properties.LegendsDBProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@Slf4j
@EnableConfigurationProperties({LegendsDBProperties.class})//开启属性注入,通过@autowired注入
@MapperScanner(basePackages = {"${legends.db.mapperScan}"})
public class MybatisPlusConfig {

    @Resource
    private LegendsDBProperties legendsDBProperties;


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        System.out.println(DbType.getDbType(legendsDBProperties.getType().toLowerCase()).getDb());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.getDbType(legendsDBProperties.getType().toLowerCase())));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
