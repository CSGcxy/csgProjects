package com.cxy.assessservice.entity.vo.ratioEntity;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-28-21:08
 */

@Data
public class UpRateRatio {

    private String ts;

    private String netSegment;

    private Double uprate;

    private Double averageRate;

    private Double maxRate;

    private Double ratio;

}
