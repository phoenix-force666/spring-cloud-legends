//package top.legendscloud.cache.service.impl;
//
//import CacheExpire;
//import top.legendscloud.cache.entity.User;
//import top.legendscloud.cache.service.IUserService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserServiceImpl implements IUserService {
//
//	@Override
//	@Cacheable(cacheNames = "users1",cacheManager = "cacheManager",key = "'cacheManager:user:'.concat(#id)")
//	public User getUser(String id) {
//		System.out.println(id+"进入实现类获取数据！");
//		User user = new User();
//		user.setId(id);
//		user.setName("香菇");
//		user.setAge(18);
//		return user;
//	}
//
//
//	@Override
//	@Cacheable(cacheNames = "users",cacheManager = "cacheManager", key = "'cacheExpire:user:'.concat(#id)")
//	@CacheExpire(expire = 1000)
//	public User cacheExpire(String id) {
//		System.out.println(id+"cacheExpire-->进入实现类获取数据！");
//		User user = new User();
//		user.setId(id);
//		user.setName("1000化石");
//		user.setAge(18);
//		return user;
//	}
//
//	@Override
//	@Cacheable(cacheNames = "users3", key = "'cacheExpire:user:'.concat(#id)")
//	public User cacheManager(String id) {
//		System.out.println(id+"cacheManager-->进入实现类获取数据！");
//		User user = new User();
//		user.setId(id);
//		user.setName("默认cacheManager");
//		user.setAge(18);
//		return user;
//	}
//
//	@Override
//	@Cacheable(cacheNames = "users2",cacheManager = "jfinetchRedisCacheManager", key = "'cacheExpire:user:'.concat(#id)")
//	public User jfinetchRedisCacheManager(String id) {
//		System.out.println(id+"jfinetchRedisCacheManager-->进入实现类获取数据！");
//		User user = new User();
//		user.setId(id);
//		user.setName("不失效");
//		user.setAge(18);
//		System.out.println(id+"不失效！");
//		return user;
//	}
//
//}
