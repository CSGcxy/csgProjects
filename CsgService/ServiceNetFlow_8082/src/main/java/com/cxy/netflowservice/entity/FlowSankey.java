package com.cxy.netflowservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FlowSankey {
    private String srcIp;
    private String dstIp;
    private Integer byteCount;
}
