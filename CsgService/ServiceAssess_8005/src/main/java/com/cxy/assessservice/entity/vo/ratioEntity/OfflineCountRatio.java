package com.cxy.assessservice.entity.vo.ratioEntity;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-29-11:37
 */
@Data
public class OfflineCountRatio {

    private String ts;

    private String netSegment;

    private Double offlineCount;

    private Double averageCount;

    private Double maxCount;

    private Double ratio;

}
