package com.cxy.assessservice.entity.vo.ratioEntity;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-29-11:20
 */

@Data
public class OnlineCountRatio {

    private String ts;

    private String netSegment;

    private Double onlineCount;

    private Double averageCount;

    private Double maxCount;

    private Double ratio;

}
