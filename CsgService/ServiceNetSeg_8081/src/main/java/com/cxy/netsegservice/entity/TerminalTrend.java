package com.cxy.netsegservice.entity;

import lombok.Data;

@Data
public class TerminalTrend {
    private String timeStamp;
    private Double uprate;
    private Double downrate;
    private Integer ondeviceCount;
}
