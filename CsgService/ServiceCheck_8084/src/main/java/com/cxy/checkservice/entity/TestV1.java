package com.cxy.checkservice.entity;

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
 * @since 2022-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TestV1对象", description="")
public class TestV1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "flag", type = IdType.INPUT)
    private String flag;

    private Float ts;

    private String srcip;

    private Integer srcport;

    private String dstip;

    private Integer dstport;

    private Integer afn;


}
