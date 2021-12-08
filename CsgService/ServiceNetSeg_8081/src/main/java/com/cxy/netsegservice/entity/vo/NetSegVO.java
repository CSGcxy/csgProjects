package com.cxy.netsegservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetSegVO {
    private List<String> intranet;
    private List<String> extranet;
}
