package com.cxy.netflowservice.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveFlowsVO {
    private String appProto;
    private String proto;
    private String srcIp;
    private String dstIp;
    private String duration;
    private double frontRate;
    private double backRate;
    //private Integer byteCount;
    private double byteCount;
}
