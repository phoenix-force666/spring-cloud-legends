package com.legends.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableCircuitBreaker
public class LgendsGatewayApplication {

//	@Bean
//	public SwaggerHeaderFilter SwaggerHeaderFilter(){
//		System.out.println("我初始化了");
//		return new SwaggerHeaderFilter();
//	}

	public static void main(String[] args) {
		SpringApplication.run(LgendsGatewayApplication.class, args);
	}

}
