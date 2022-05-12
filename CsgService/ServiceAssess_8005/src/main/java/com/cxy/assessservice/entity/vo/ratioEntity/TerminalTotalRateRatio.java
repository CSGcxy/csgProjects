package com.cxy.assessservice.entity.vo.ratioEntity;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-28-21:08
 */

@Data
public class TerminalTotalRateRatio {

    // 终端ip
    private String ip;

    // 时间
    private String ts;

    // 位置
    private String location;

    // 上行速率(仅用于展示)
    private Double uprate;

    // 下行速率(仅用于展示)
    private Double downrate;

    // 总速率
    private Double totalrate;

    // 总速率的平均速率
    private Double averageRate;

    // 总速率的最大速率
    private Double maxRate;

    // 总速率和总速率的平均速率的比值
    private Double ratio;

    // 总速率得分
    private Double totalrateScore;
}
