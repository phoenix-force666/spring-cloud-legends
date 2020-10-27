package com.legends.utils.mock.util;

import com.legends.utils.mock.entity.IdCard;
import org.junit.Test;

public class IdCardGeneratorTest {
    //生成身份证
    @Test
    public void testGenerate(){
        System.out.println(IdCardGenerator.generate());
    }

    /**
     * 生成身份信息
     */
    @Test
    public void testGenerateIdCard(){
        IdCard idCard=IdCardGenerator.generateIdCard();
        System.out.println(idCard);
    }

}