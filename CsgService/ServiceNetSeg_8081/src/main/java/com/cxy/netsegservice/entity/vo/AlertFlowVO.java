package com.cxy.netsegservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertFlowVO {
    private String timestamp;
    private String NETWORKSEG;
    private Integer alertFlow;
}
