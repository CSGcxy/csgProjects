package com.cxy.netflowservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FlowSankey {
    private String source;
    private String target;
    private Integer value;
}
