package com.cxy.assessservice.entity.vo.ratioEntity;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-29-10:55
 */
@Data
public class DownRateRatio {

    private String ts;

    private String netSegment;

    private Double downrate;

    private Double averageRate;

    private Double maxRate;

    private Double ratio;

}
