package com.aklry.quiz.model.dto.question;

import lombok.Data;

/**
 * 题目答案DTO(用户AI评分)
 * @author <a href="https://github.com/aklry">aklry</a>
 */
@Data
public class QuestionAnswerDto {
    /**
     * 答案
     */
    private String userAnswer;
    /**
     * 题目
     */
    private String title;
}
