//package com.legends.cloud.cache;
//
//import IRedisService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = LegendsCacheApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableTransactionManagement //如果mybatis中service实现类中加入事务注解，需要此处添加该注解
//@EnableAutoConfiguration
//public class RedisTest {
//
//	@Autowired
//	private RedisTemplate<String, Object> template;
//
//
//
//	@Test
//	public void rightPopAndLeftPush() {
//	ListOperations<String, Object> operations =  this.template.opsForList();
//	System.out.println(operations.leftPush("left","left1"));//1
//	System.out.println(operations.leftPush("left","left2"));//2
//	System.out.println(operations.leftPush("left","left3"));//3
//	System.out.println(operations.rightPush("right", "right1"));//1
//	System.out.println(operations.rightPush("right", "right2"));//2
//	System.out.println(operations.rightPush("right", "right3"));//3
//	System.out.println(operations.rightPopAndLeftPush("left", "right"));
//	}
//
//	@Test
//	public void remove() {
//		ListOperations<String, Object> operations =  this.template.opsForList();
//		for (int j = 0; j < 3; j++) {
//			Long num = operations.leftPush("ceshi", String.valueOf(j));
//			System.out.println("num:"+num);
//		}
//		operations.remove("ceshi", 2, "0");
//		System.out.println(operations.range("ceshi", 0, -1));
//		System.out.println(operations.size("ceshi"));
//	}
//
//	@Test
//	public void leftPush() {
//
//		ListOperations<String, Object> operations =  this.template.opsForList();
//		for (int j = 0; j < 3; j++) {
//			Long num = operations.leftPush("ceshi", String.valueOf(j));
//			System.out.println("num:"+num);
//		}
//		System.out.println(operations.range("ceshi", 0, -1));
//		System.out.println(operations.size("ceshi"));
//	}
//
//	@Test
//	public void set() {
//		template.opsForValue().set("herionTest","herionTest2019");
//	}
//
//	@Test
//	public void del() {
//		template.delete("herionTest");
//	}
//}
