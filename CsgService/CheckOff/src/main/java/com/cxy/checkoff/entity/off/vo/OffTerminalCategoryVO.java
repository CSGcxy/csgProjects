package com.cxy.checkoff.entity.off.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffTerminalCategoryVO {
    private List<String> category;
    private List<Integer> offTerminalCount;
}
