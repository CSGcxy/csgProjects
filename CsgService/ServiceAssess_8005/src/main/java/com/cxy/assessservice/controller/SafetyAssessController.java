package com.cxy.assessservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.assessservice.entity.SafetyAssess;
import com.cxy.assessservice.service.SafetyAssessService;
import com.cxy.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cxy
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/safetyAssess")
public class SafetyAssessController {

    @Autowired
    SafetyAssessService safetyAssessService;

    /**
     * <p>测试是否能连接到数据库</p>
     */
    @ApiOperation(value = "测试是否能连接到数据库")
    @GetMapping("/test")
    public R getTest(){

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("score_one",100);
        //以下是mybatis-plus写法，可以用mp也可以自己写xml
        return R.ok().data("test", safetyAssessService.getOne(wrapper));
    }


    /**
     * <p>查询主站6个指标评分及总分</p>
     */
    @ApiOperation(value = "查询主站6个指标评分及总分")
    @GetMapping("/getStationScore")
    public R getStationScore(){
        // 查询最近某时间区间内6个指标的6次统计结果
        List<SafetyAssess> quotaScoreList = safetyAssessService.getQuotaScore();
        // 计算客观权重下总分
        Double objectiveTotalScore = safetyAssessService.getTotalScore();
        // 计算主观总分下总分
        Double subjectiveTotalScore = safetyAssessService.getSubjectiveTotalScore();
        return R.ok().data("quotaScoreList",quotaScoreList).data("objectiveTotalScore",objectiveTotalScore).data("subjectiveTotalScore",subjectiveTotalScore);
    }


}

