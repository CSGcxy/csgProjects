package com.cxy.netflow.entity.netseg;

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
@ApiModel(value="NetSegTotalBytes对象", description="")
public class NetSegTotalBytes implements Serializable {

    private String timestamp;

    private Integer totalbytes;

}
