package com.aklry.quiz.scoring;

import com.aklry.quiz.model.entity.App;
import com.aklry.quiz.model.entity.UserAnswer;

import java.util.List;

/**
 * 评分策略接口
 * @author <a href="https://github.com/aklry">aklry</a>
 */
public interface ScoringStrategy {
    /**
     * 执行评分
     * @param choices 答案列表
     * @param app 应用
     * @return
     * @throws Exception
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}
