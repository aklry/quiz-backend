package com.aklry.quiz.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * app应用类型枚举
 * @author <a href="https://github.com/aklry">aklry</a>
 */
public enum AppTypeEnum {
    SCORE("得分类", 0),
    TEST("测评类", 1);

    private final String text;
    private final int value;

    AppTypeEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据值获取枚举
     * @param value 值 0,1,2
     * @return ReviewStatusEnum
     */
    public static AppTypeEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (AppTypeEnum reviewStatusEnum : AppTypeEnum.values()) {
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
