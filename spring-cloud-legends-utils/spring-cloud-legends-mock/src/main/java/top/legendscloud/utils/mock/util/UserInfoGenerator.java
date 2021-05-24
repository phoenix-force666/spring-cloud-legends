package top.legendscloud.utils.mock.util;

import top.legendscloud.utils.mock.entity.IdCard;
import top.legendscloud.utils.mock.entity.UserInfo;

import java.util.Map;

public class UserInfoGenerator {

    public static UserInfo generator(){
        UserInfo userInfo=new UserInfo();
        userInfo.setCardNumber(CardNum.generateMasterCardNumber());
        IdCard idCard=IdCardGenerator.generateIdCard();
        userInfo.setIdCard(idCard.getIdCard());
        userInfo.setSex(idCard.getSex());
        userInfo.setArea(idCard.getArea());
        userInfo.setAge(idCard.getAge());
        userInfo.setBirthday(idCard.getBirthday());
        Map map=JavaName.getAddress();
        userInfo.setName(map.get("name").toString());
        userInfo.setRoad(map.get("road").toString());
        userInfo.setTel(map.get("tel").toString());
        userInfo.setEmail(map.get("email").toString());
        userInfo.setAddr(userInfo.getArea()+userInfo.getRoad());
        System.out.println(userInfo.toString());
        return userInfo;
    }
}