package com.cxy.netflowservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author cxy
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Flows")
@ApiModel(value="Flows对象", description="")
public class Flows implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "pk", type = IdType.INPUT)
    private String pk;

    private Integer timestamp;

    private Integer flowId;

    private Integer flowStartTimestamp;

    private String appProto;

    private String proto;

    private String client;

    private String server;

    private String srcIp;

    private String dstIp;

    private Integer srcPort;

    private Integer dstPort;

    private String duration;

    private Integer frontByte;

    private Integer backByte;

    private Double frontRate;

    private Double backRate;

    private Integer byteCount;


}
