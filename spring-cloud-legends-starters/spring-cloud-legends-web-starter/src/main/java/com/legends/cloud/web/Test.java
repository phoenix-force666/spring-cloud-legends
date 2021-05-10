package com.legends.cloud.web;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Cache<String,List<User>> cache = CacheBuilder.newBuilder().build();

        List<User> list=new ArrayList<>();
        User user1=new User("herion",18);
        User user2=new User("herion1",19);
        User user3=new User("herion2",11);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        cache.put("aa",list);
        System.out.println("1-------->:"+ JSON.toJSONString(cache.getIfPresent("aa")));
        List<User> list1=new ArrayList<>();
        User user5=new User("herion5",1);
        User user4=new User("herion14",29);
        list1.add(user5);
        list1.add(user4);
        cache.put("aa",list1);
        System.out.println("1-------->:"+ JSON.toJSONString(cache.getIfPresent("aa")));
    }

    static class User{
        public User(String name,int age){
            this.age=age;
            this.name=name;
        }
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
