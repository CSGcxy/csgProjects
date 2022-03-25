package com.cxy.checkservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AfnPeriod {
    private String period;
    private Integer afn;
    private Integer count;
}
