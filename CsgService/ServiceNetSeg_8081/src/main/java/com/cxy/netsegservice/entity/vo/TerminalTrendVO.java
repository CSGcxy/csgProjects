package com.cxy.netsegservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalTrendVO {
    private List<String> timeStamp;
    private List<String> uprate;
    private List<String> downrate;
    private List<Integer> OnlineNum;
}
