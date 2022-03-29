package com.cxy.assessservice.entity.vo;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-28-20:44
 */
@Data
public class SegAllScore {

    /**
     * 上行速率评分
     */
    private Double uprateScore;

    /**
     * 下行速率评分
     */
    private Double downrateScore;

    /**
     * 在线设备评分
     */
    private Double ondevicecountScore;

    /**
     * 离线设备评分
     */
    private Double offdevicecountScore;

    /**
     * 告警流数评分
     */
    private Double alertflowScore;

    /**
     * 活跃流数评分
     */
    private Double activeflowScore;


}
