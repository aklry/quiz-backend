package com.aklry.quiz.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Cos 操作测试
 *
 * @author <a href="https://github.com/aklry">aklry</a>
 * 
 */
@SpringBootTest
class CosManagerTest {

    @Resource
    private OssManager ossManager;

    @Test
    void putObject() {
        ossManager.putObject("test", "test.json");
    }
}