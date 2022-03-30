package com.cxy.netflow.entity.flows;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FlowSankey {
    private String source;
    private String target;
    private Integer value;
}
