package com.aklry.quiz.scoring;

import cn.hutool.json.JSONUtil;
import com.aklry.quiz.model.dto.question.QuestionContentDto;
import com.aklry.quiz.model.entity.App;
import com.aklry.quiz.model.entity.Question;
import com.aklry.quiz.model.entity.ScoringResult;
import com.aklry.quiz.model.entity.UserAnswer;
import com.aklry.quiz.model.vo.QuestionVO;
import com.aklry.quiz.service.QuestionService;
import com.aklry.quiz.service.ScoringResultService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 自定义测评类应用评分策略
 * @author <a href="https://github.com/aklry">aklry</a>
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomTestScoringStrategy implements ScoringStrategy {
    @Resource
    private QuestionService questionService;
    @Resource
    private ScoringResultService scoringResultService;
    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        // 1. 根据id查询到题目和题目结果信息
        Long appId = app.getId();
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class).eq(ScoringResult::getAppId, appId)
        );
        // 2. 统计用户每个选择对应的属性个数: 如I ->10个 E -> 5个

        // 初始化一个Map对象，用于存储每个选项的计数
        Map<String, Integer> optionCount = new HashMap<>();
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDto> questionVOQuestionContent = questionVO.getQuestionContent();
        // 遍历题目列表
        for (QuestionContentDto questionContentDto : questionVOQuestionContent) {
            // 遍历答案列表
            for (String answer : choices) {
                // 遍历题目中的选项
                for (QuestionContentDto.Result option : questionContentDto.getOptions()) {
                    // 如果答案和选项的key匹配
                    if (option.getKey().equals(answer)) {
                        // 获取选项的result属性
                        String result = option.getResult();

                        // 如果result属性不在optionCount中，则将其初始化为0
                        optionCount.putIfAbsent(result, 0);

                        // 在optionCount中增加计数
                        optionCount.put(result, optionCount.get(result) + 1);
                    }
                }
            }
        }
        // 3. 遍历每种评分结果，计算哪个结果的评分最高
        int maxCount = 0;
        ScoringResult maxScoringResult = scoringResultList.get(0);

        for (ScoringResult scoringResult : scoringResultList) {
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(), String.class);
            int score = resultProp.stream().mapToInt(prop -> optionCount.getOrDefault(prop, 0)).sum();

            if (score > maxCount) {
                maxCount = score;
                maxScoringResult = scoringResult;
            }
        }
        // 4. 构造返回值，填充答案对象的属性
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());


        return userAnswer;
    }
}
