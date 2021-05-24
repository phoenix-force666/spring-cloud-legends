package top.legendscloud.gateway.config;

import top.legendscloud.gateway.filter.SwaggerHeaderFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * swagger 聚合过滤器自动装配
 * @Description
 * @Author herion
 * @Date 2020-08-13 17:49
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class SwaggerAutoConfig {

    @Bean
    @ConditionalOnMissingBean
	public SwaggerHeaderFilter SwaggerHeaderFilter(){
		log.info("我初始化了");
		return new SwaggerHeaderFilter();
	}
}