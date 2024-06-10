package com.aklry.quiz.service;

import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 * @author <a href="https://github.com/aklry">aklry</a>
 * 
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String userAvatar = "https://www.baidu.com/img/bd_logo1.png";
        try {
            long result = userService.userRegister(userAccount, userPassword, checkPassword, userAvatar);
            Assertions.assertEquals(-1, result);
            userAccount = "yu";
            result = userService.userRegister(userAccount, userPassword, checkPassword, userAvatar);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }
}
