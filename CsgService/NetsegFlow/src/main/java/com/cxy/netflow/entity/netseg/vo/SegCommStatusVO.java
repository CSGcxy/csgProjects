package com.cxy.netflow.entity.netseg.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SegCommStatusVO {

    private String ip;

    private String location;

    private Integer flows;

    private String name;

    private long totalrecvbytes;

    private long totalbytes;

    private Double rates;

    private String lastseen;
}
