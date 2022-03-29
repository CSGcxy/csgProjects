package com.cxy.assessservice.entity.vo.ratioEntity;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-29-16:11
 */

@Data
public class ActiveFlowCountRatio {

    private String ts;

    private String netSegment;

    private Double activeFlowCount;

    private Double averageCount;

    private Double maxCount;

    private Double ratio;

}
