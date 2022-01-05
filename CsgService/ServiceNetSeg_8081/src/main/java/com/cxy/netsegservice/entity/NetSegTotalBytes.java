package com.cxy.netsegservice.entity;

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
@ApiModel(value="NetSegTotalBytes对象", description="")
public class NetSegTotalBytes implements Serializable {

    private String timestamp;

    private Integer totalbytes;

}
