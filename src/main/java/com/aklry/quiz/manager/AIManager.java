package com.aklry.quiz.manager;

import com.aklry.quiz.common.ErrorCode;
import com.aklry.quiz.exception.BusinessException;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * AI操作
 * @author <a href="https://github.com/aklry">aklry</a>
 */
@Component
public class AIManager {
    @Resource
    private GenerationParam generationParam;


    /**
     * 通用请求
     * @param messages 输入的文本
     * @return AI的回答
     */
    public String doRequest(List<Message> messages) {
        generationParam.setMessages(messages);
        try {
            Generation gen = new Generation();
            GenerationResult result = gen.call(generationParam);
            return result.getOutput().getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     *
     * @param systemContent 系统消息
     * @param userContent 用户消息
     * @return
     */
    public String doRequest(String systemContent, String userContent) {
        List<Message> messages = new ArrayList<>();
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content(systemContent).build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(userContent).build();
        messages.add(systemMsg);
        messages.add(userMsg);
        return doRequest(messages);
    }
}
