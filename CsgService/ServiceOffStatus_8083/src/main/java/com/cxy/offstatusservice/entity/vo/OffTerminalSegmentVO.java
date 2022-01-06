package com.cxy.offstatusservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffTerminalSegmentVO {
    private List<String> netSegment;
    private List<Integer> offTerminalCount;
}
