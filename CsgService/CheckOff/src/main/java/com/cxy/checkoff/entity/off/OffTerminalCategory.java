package com.cxy.checkoff.entity.off;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OffTerminalCount对象", description="")
public class OffTerminalCategory implements Serializable {

    private String category;
    private Integer offTerminalCount;

}
