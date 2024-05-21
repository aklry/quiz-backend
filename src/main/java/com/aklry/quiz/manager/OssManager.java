package com.aklry.quiz.manager;

import com.aklry.quiz.config.OssClientConfig;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * oss 对象存储操作
 *
 * @author <a href="https://github.com/aklry">aklry</a>
 */
@Component
public class OssManager {

    @Resource
    private OssClientConfig ossClientConfig;

    @Resource
    private OSSClient ossClient;

    /**
     * 上传对象
     *
     * @param key           唯一键
     * @param localFilePath 本地文件路径
     * @return
     */
    public PutObjectResult putObject(String key, String localFilePath) {
        PutObjectResult putObjectResult = ossClient.putObject(ossClientConfig.getBucketName(), key, new File(localFilePath));
        return putObjectResult;
    }

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     * @return
     */
    public PutObjectResult putObject(String key, MultipartFile file) {
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(ossClientConfig.getBucketName(), key,
                    file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ossClient.putObject(putObjectRequest);
    }
}
