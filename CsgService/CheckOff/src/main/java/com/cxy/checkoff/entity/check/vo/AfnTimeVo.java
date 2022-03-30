package com.cxy.checkoff.entity.check.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Lishuguang
 * @create 2022-03-18-11:12
 */
@Data
public class AfnTimeVo {

    private List<String> timeList;

    private Map<Integer, List<Integer>> afnHashMap;

    private Map<Integer, Integer> afnTotalHashMap;

}
