package com.legends.cloud.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LegendsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegendsEurekaApplication.class, args);
	}

}
