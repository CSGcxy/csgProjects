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

    // 10分钟内出现的网段
    List<String> segmentList;

    // 多个时间范围内的网段总分及6个指标分数详情
    List<List<SegScoreEntityVo>> timeRangeScore;

    // 数组形式返回各个网段各个时间段的总分
    List<List<Double>> segAllTimeScoreList;

    // 最新时间的各个网段6个指标评分详情
    List<List<Double>> latestTimeSegDeatils;
}
