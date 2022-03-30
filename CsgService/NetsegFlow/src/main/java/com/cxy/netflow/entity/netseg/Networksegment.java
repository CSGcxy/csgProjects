package com.cxy.netflow.entity.netseg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Networksegment对象", description="")
public class Networksegment implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "PK", type = IdType.INPUT)
    private String pk;

    private long timestamp;

    private String networkseg;

    private Integer upbytes;

    private Integer downbytes;

    private Double uprate;

    private Double downrate;

    @TableField("Alert_flow")
    private Integer alertFlow;

    private Integer ondevicecount;

    private Integer offdevicecount;

    @TableField("Active_Flow_Count")
    private Integer activeFlowCount;

    private Integer totalbytes;


}
