package com.cxy.netflowservice.entity.vo;

import com.cxy.netflowservice.entity.FlowSankey;
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
