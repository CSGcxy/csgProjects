package com.cxy.offstatusservice.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OffTerminalCount对象", description="")
public class OffTerminalCount implements Serializable {

    private String timestamp;
    private Integer offTerminalCount;

}
