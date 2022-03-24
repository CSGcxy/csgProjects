package com.cxy.assessservice.entity;

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
 * @since 2022-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SafetyAssess对象", description="")
public class SafetyAssess implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ts;

    private Double scoreOne;

    private Double scoreTwo;

    private Double scoreThree;

    private Double scoreFour;

    private Double scoreFive;

    private Double scoreSix;


}
