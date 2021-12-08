package com.cxy.netsegservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapVO {
    private List<String> locations;
    private List<Integer> count;
}
