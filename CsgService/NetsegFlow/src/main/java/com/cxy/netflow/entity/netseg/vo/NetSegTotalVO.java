package com.cxy.netflow.entity.netseg.vo;

import lombok.Data;

@Data
public class NetSegTotalVO {
    private String networkseg;

    private long upbytes;

    private long downbytes;

    private Double uprate;

    private Double downrate;

    private Integer alertFlow;

    private Integer ondevicecount;

    private Integer offdevicecount;

    private Integer activeFlowCount;
}
