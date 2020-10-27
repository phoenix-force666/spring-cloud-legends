package com.legends.utils.mock.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UserInfo extends IdCard{
    private String name;
    private String road;
    private String tel;
    private String email;
    private String cardNumber;
    private String addr;

}