package com.aklry.quiz.scoring;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author aklry
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScoringStrategyConfig {
    /**
     * 应用类型
     *
     * @return int
     */
    int appType();

    /**
     * 评分策略
     * @return int
     */
    int scoringStrategy();
}
