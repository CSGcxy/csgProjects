package com.cxy.checkoff.entity.off;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OffTerminalSegment对象", description="")
public class OffTerminalSegment implements Serializable {

    private String netSegment;
    private Integer offTerminalCount;

}
