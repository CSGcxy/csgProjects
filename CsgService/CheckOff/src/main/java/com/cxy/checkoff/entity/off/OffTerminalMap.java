package com.cxy.checkoff.entity.off;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OffTerminalMap对象", description="")
public class OffTerminalMap implements Serializable {

    private String location;
    private Integer offTerminalCount;

}
