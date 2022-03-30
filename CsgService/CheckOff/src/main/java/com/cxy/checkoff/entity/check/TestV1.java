package com.cxy.checkoff.entity.check;

import com.baomidou.mybatisplus.annotation.IdType;
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
