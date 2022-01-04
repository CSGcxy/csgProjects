package com.cxy.netflowservice.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveFlowsVO {
    private String APP_PROTO;
    private String PROTO;
    private String SRC_IP;
    private String DST_IP;
    private String DURATION;
    private double FRONT_RATE;
    private double BACK_RATE;
    private Integer BYTE_COUNT;
}
