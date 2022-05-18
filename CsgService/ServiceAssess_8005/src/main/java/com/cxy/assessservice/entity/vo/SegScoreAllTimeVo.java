package com.cxy.assessservice.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Lishuguang
 * @create 2022-05-18-9:22
 */

@Data
public class SegScoreAllTimeVo {

    // 时间区间
    List<String> timeArray;

    // 多个时间范围内的网段总分及6个指标分数详情
    List<List<SegScoreEntityVo>> timeRangeScore;
}
