package com.cxy.checkoff.entity.check.vo;

import lombok.Data;

/**
 * @author Lishuguang
 * @create 2022-03-17-20:03
 */
@Data
public class PacketDetailsVo {

    private String flag;

    private String ts;

    private String srcip;

    private Integer srcport;

    private String dstip;

    private Integer dstport;

    private Integer afn;

}
