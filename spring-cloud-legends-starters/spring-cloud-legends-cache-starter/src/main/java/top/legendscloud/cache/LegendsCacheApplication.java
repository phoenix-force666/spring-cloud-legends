//package top.legendscloud.cache;
//
//import IRedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.ComponentScan;
//
//@SpringBootApplication
//@EnableAutoConfiguration
//@EnableCaching
//public class LegendsCacheApplication implements ApplicationRunner{
//
//	@Autowired
//	private IRedisService redisService;
//
//	public static void main(String[] args) {
//		SpringApplication.run(LegendsCacheApplication.class, args);
//	}
//
//	@Override
//	public void run(ApplicationArguments applicationArguments) throws Exception {
//		redisService.set("herionRedisService","herionRedisService");
//	}
//}
