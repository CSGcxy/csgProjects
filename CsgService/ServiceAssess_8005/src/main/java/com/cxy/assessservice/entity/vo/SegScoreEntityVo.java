package com.cxy.assessservice.entity.vo;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-28-20:41
 */
@Data
public class SegScoreEntityVo {

    // 网段名
    private String segment;

    // 时间
    private String ts;

    // 网段总分(熵值法)
    private Double totalScore;

    // 6个指标各自评分
    private SegAllScore segAllScore;

}
