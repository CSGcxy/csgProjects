package com.cxy.assessservice.entity.vo.ratioEntity;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-29-15:55
 */

@Data
public class AlertFlowCountRatio {

    private String ts;

    private String netSegment;

    private Double alertFlowCount;

    private Double averageCount;

    private Double maxCount;

    private Double ratio;

}
