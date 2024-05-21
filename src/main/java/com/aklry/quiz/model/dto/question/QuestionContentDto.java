package com.aklry.quiz.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionContentDto {
    /**
     * 题目选项列表
     */
    private List<Result> options;
    /**
     * 题目标题
     */
    private String title;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String result;
        private int score;
        private String value;
        private String key;
    }
}