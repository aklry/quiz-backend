package com.aklry.quiz.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * AI创建题目请求
 *
 * @author <a href="https://github.com/aklry">aklry</a>
 */
@Data
public class AIGenerateQuestionRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 应用ID
     */
    private Long appId;
    /**
     * 题目数量
     */
    private Integer QuestionNumber = 10;

    /**
     * 选项数量
     */
    private Integer optionNumber = 2;
}
