package com.cxy.netflow.entity.flows.vo;

import com.cxy.netflow.entity.flows.FlowSankey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowSankeyVO {
    private List<FlowSankey> flowSankeys;
    private HashSet<String> ip;
}
