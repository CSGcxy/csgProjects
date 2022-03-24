package com.cxy.assessservice.service;

import com.cxy.assessservice.entity.SafetyAssess;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxy
 * @since 2022-03-23
 */
public interface SafetyAssessService extends IService<SafetyAssess> {

    List<SafetyAssess> getQuotaScore();

    // 计算客观权重下总分
    Double getTotalScore();

    // 计算主观总分下总分
    Double getSubjectiveTotalScore();

    // xml方式获取6个指标的6次取值
//    List<SafetyAssess> getQuotaScoreList();

}
