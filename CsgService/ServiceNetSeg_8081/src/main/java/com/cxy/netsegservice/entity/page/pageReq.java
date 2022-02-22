package com.cxy.netsegservice.entity.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class pageReq implements Serializable {
    /**
     * 每页显示大小
     */
    private long  size;

    /**
     * 当前页码
     */
    private  long current;

    /**
     * 最大页数
     */
    private  long maxCurrent;

    /**
     * 数据总条数
     */
    private  long total;
}
