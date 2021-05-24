//package top.legendscloud.cache.service.impl;
//
//import top.legendscloud.cache.LegendsCacheApplication;
//import top.legendscloud.cache.entity.User;
//import top.legendscloud.cache.service.IUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//
///**
// * Created by zhanghai on 2019/5/17.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = LegendsCacheApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class UserServiceImplTest {
//
//    @Autowired
//    private IUserService userService;
//
//    @Test
//    public void testGetUser() throws Exception {
//        User user=userService.getUser("H125");
//        System.out.println(user.toString());
//    }
//
//    @Test
//    public void jfinetchRedisCacheManager() throws Exception {
//        userService.jfinetchRedisCacheManager("M129");
//    }
//
//    @Test
//    public void testCacheExpire() throws Exception {
//        User user=userService.cacheExpire("E127");
//        System.out.println(user.toString());
//
//        User user1=userService.cacheExpire("E126");
//        System.out.println(user1.toString());
//    }
//
//    @Test
//    public void testCacheManager() throws Exception {
//        userService.cacheManager("C111");
//    }
//}