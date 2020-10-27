package com.legends.utils.mock.util;

import com.legends.utils.mock.entity.UserInfo;
import org.junit.Test;

public class UserInfoGeneratorTest {

    /**
     * 生成用户信息
     * 姓名、性别、地址、手机号、银行卡号、身份证号、年龄
     */
    @Test
    public void testGenerator(){
        UserInfo userInfo=UserInfoGenerator.generator();
    }
}