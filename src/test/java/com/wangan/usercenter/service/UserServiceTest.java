package com.wangan.usercenter.service;
import java.util.Date;


import com.wangan.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 */
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void TestAddUser(){
        User user = new User();


        user.setUsername("daxia");
        user.setUserAccount("daxia123");
        user.setAvatarUrl("https://tse3-mm.cn.bing.net/th/id/OIP-C.g9UbVfyVZX-SfD09JcYr5QHaEK?pid=ImgDet&rs=1");
        user.setGender((byte) 0);
        user.setUserPassword("");
        user.setPhone("111111");
        user.setEmail("222222@qq.com");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete((byte) 0);
        user.setUserRole(0);
        user.setPlanetCode("001");

        boolean result = userService.save(user);
        System.out.println(user.getId());

    }

    @Test
    void userRegister() {
//        String userAccount = "yupi";
//        String userPassword = "";
//        String checkPassword = "123456";
//        long result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yu";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yupi";
//        userPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yu pi";
//        userPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        checkPassword = "123456789";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        userAccount = "dogYupi";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yupi";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
        String userAccount = "wang";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertTrue(result > 0);
    }
}