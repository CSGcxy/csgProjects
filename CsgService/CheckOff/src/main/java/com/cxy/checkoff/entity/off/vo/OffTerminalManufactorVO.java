package com.cxy.checkoff.entity.off.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffTerminalManufactorVO {
    private List<String> manufactor;
    private List<Integer> offTerminalCount;
}