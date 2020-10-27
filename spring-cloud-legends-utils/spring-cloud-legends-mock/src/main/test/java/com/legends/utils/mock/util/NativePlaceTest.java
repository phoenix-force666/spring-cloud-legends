package com.legends.utils.mock.util;

import org.junit.Test;

public class NativePlaceTest {

    @Test
    public void test(){
        String idNum=IdCardGenerator.generate();
        int nativePlaceCode=Integer.parseInt(idNum.substring(0, 6));
        System.out.println(nativePlaceCode);
        String nativePlace=NativePlace.getNativePlace(nativePlaceCode);
        System.out.println(nativePlace);
    }
}