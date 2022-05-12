package com.cxy.assessservice.entity.vo;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-28-20:41
 */
@Data
public class TerminalScoreEntityVo {

    // 终端ip
    private String ip;

    // 时间
    private String ts;

    // 位置
    private String location;

    // 上行速率
    private Double uprate;

    // 下行速率
    private Double downrate;

    // 总速率
    private Double totalrate;

    // 总速率得分
    private Double totalrateScore;
}
