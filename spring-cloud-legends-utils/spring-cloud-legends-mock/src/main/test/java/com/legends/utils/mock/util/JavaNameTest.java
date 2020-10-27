package com.legends.utils.mock.util;

import org.junit.Test;

public class JavaNameTest {
    @Test
    public void test(){
        String name=JavaName.getAddress().get("name").toString();
        String sex=JavaName.getAddress().get("sex").toString();
        String road=JavaName.getAddress().get("road").toString();
        String tel=JavaName.getAddress().get("tel").toString();
        String email=JavaName.getAddress().get("email").toString();
        System.out.println(name);
        System.out.println(sex);
        System.out.println(road);
        System.out.println(tel);
        System.out.println(email);
    }
}