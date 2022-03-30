package com.cxy.netflow.entity.netseg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetSegTotalBytesVO {
    private List<String> timestamp;
    private List<Integer> totalBytes;
}
