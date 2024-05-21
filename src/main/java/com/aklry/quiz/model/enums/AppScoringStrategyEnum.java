package com.aklry.quiz.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * app评分策略枚举
 * @author <a href="https://github.com/aklry">aklry</a>
 */
public enum AppScoringStrategyEnum {
    CUSTOM("自定义", 0),
    AI("AI", 1);

    private final String text;
    private final int value;

    AppScoringStrategyEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据值获取枚举
     * @param value 值 0,1,2
     * @return ReviewStatusEnum
     */
    public static AppScoringStrategyEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (AppScoringStrategyEnum reviewStatusEnum : AppScoringStrategyEnum.values()) {
            if (reviewStatusEnum.value == value) {
                return reviewStatusEnum;
            }
        }
        return null;
    }

    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
