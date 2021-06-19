package top.legendscloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LgendsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LgendsGatewayApplication.class, args);
	}

}
