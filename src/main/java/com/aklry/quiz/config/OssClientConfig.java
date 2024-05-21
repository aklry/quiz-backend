package com.aklry.quiz.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云对象存储客户端
 *
 * @author <a href="https://github.com/aklry">aklry</a>
 * 
 */
@Configuration
@ConfigurationProperties(prefix = "oss.client")
@Data
public class OssClientConfig {

    /**
     * accessKey
     */
    private String accessKeyId;

    /**
     * secretKey
     */
    private String accessKeySecret;

    /**
     * 区域
     */
    private String endpoint;

    /**
     * 桶名
     */
    private String bucketName;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}