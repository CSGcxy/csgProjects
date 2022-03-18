package com.cxy.checkservice.entity.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author Lishuguang
 * @create 2022-03-18-11:12
 */
@Data
public class AfnTimeVo {

    private List<String> timeList;

    private HashMap<Integer, List<Integer>> afnHashMap;

    private HashMap<Integer, Integer> afnTotalHashMap;

}
