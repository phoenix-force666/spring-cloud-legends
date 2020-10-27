package com.legends.utils.mock.util;

import org.junit.Test;

public class CardNumTest {

    /**
     * 生成银行卡信息
     */
    @Test
    public void testGenerateMasterCardNumber(){
        System.out.println(CardNum.generateMasterCardNumber());
    }

}