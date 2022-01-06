package com.cxy.offstatusservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffTerminalMapVO {
    private List<String> location;
    private List<Integer> offTerminalCount;
}
