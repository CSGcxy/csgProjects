package com.cxy.netflowservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FlowSankey {
    private String SRC_IP;
    private String DST_IP;
    private Integer BYTE_COUNT;
}
