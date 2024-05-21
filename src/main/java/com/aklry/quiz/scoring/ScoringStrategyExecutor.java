package com.aklry.quiz.scoring;

import com.aklry.quiz.common.ErrorCode;
import com.aklry.quiz.exception.BusinessException;
import com.aklry.quiz.model.entity.App;
import com.aklry.quiz.model.entity.UserAnswer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评分策略执行器
 * @author aklry
 */
@Service
public class ScoringStrategyExecutor {
    private List <ScoringStrategy> scoringStrategyList;

    public UserAnswer doScore(List<String> choicesList, App app) throws Exception {
        Integer appType = app.getAppType();
        Integer appScoringStrategy = app.getScoringStrategy();
        if (appType == null || appScoringStrategy == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
        }
        // 根据注解获取策略
        for (ScoringStrategy strategy : scoringStrategyList) {
            if (strategy.getClass().isAnnotationPresent(ScoringStrategyConfig.class)) {
                ScoringStrategyConfig scoringStrategyConfig = strategy.getClass().getAnnotation(ScoringStrategyConfig.class);
                if (scoringStrategyConfig.appType() == appType && scoringStrategyConfig.scoringStrategy() == appScoringStrategy) {
                    return strategy.doScore(choicesList, app);
                }
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
    }
}
