package com.aklry.quiz.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import com.aklry.quiz.common.BaseResponse;
import com.aklry.quiz.common.ErrorCode;
import com.aklry.quiz.common.ResultUtils;
import com.aklry.quiz.config.OssClientConfig;
import com.aklry.quiz.constant.FileConstant;
import com.aklry.quiz.exception.BusinessException;
import com.aklry.quiz.manager.OssManager;
import com.aklry.quiz.model.dto.file.UploadFileRequest;
import com.aklry.quiz.model.enums.FileUploadBizEnum;
import com.aklry.quiz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 文件接口
 *
 * @author <a href="https://github.com/aklry">aklry</a>
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private UserService userService;

    @Resource
    private OssManager ossManager;

    @Resource
    private OssClientConfig ossClientConfig;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param uploadFileRequest
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile multipartFile,
                                           UploadFileRequest uploadFileRequest, HttpServletRequest request) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, fileUploadBizEnum);
        // 文件目录：根据业务、用户来划分
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + multipartFile.getOriginalFilename();
        String dateTime = new DateTime().toString("yyyy/MM/dd");
        filename = dateTime + "/" + filename;
        try {
            ossManager.putObject(filename, multipartFile);
            // 返回可访问地址
            return ResultUtils.success(FileConstant.OSS_HOST + ossClientConfig.getEndpoint() + '/' + filename);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filename, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
