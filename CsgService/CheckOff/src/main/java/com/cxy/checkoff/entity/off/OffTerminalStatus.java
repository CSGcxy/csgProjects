package com.cxy.checkoff.entity.off;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OffTerminalStatus对象", description="")
public class OffTerminalStatus implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "PK", type = IdType.INPUT)
    private String pk;

    private long timestamp;

    private Integer dno;

    private String ip;

    private String zdlx;

    private String zddz;

    private String zdcj;

    private String zdlb;

}
