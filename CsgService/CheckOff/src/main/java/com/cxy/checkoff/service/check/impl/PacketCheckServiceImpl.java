package com.cxy.checkoff.service.check.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.checkoff.entity.check.TestV1;
import com.cxy.checkoff.entity.check.vo.*;
import com.cxy.checkoff.mapper.PacketCheckMapper;
import com.cxy.checkoff.service.check.PacketCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxy
 * @since 2022-03-16
 */
@Service
@Slf4j
public class PacketCheckServiceImpl extends ServiceImpl<PacketCheckMapper, TestV1> implements PacketCheckService {

    @Autowired
    private PacketCheckMapper packetCheckMapper;

    // 查询不同afn包的数目
    @Override
    public List<PacketCountVo> getPacketCount() {
        List<PacketCountVo> packetCountVoList = packetCheckMapper.getPacketCount();
        return packetCountVoList;
    }

    // 查询合格包和不合格包数目
    @Override
    public PacketUnQualifiedCount getUnqualifiedPacketCount() {
        return packetCheckMapper.getQualifiedCount();
    }

    // 每隔5秒查询不同的合格包数目
    @Override
    public AfnTimeVo getDifAfnCount(Integer second) {
        List<AfnPeriod> afnCount = packetCheckMapper.getDifAfnCount(second);
        List<Integer> difAfns = packetCheckMapper.selectDifAfns();

        Map<Integer, List<Integer>> afnMap = new HashMap<>();
        Map<String, List<AfnPeriod>> afnListByTime = new HashMap<>();//存储每个时间段的数据
        Set<Integer> afnSet = new HashSet<>();//记录afn种类

        //记录过去最新20s内出现的合规afn种类
        for (Integer afn : difAfns) {
            afnMap.put(afn, new LinkedList<Integer>());
            afnSet.add(afn);
        }
        //使用set对日期进行去重，然后再转换为list
        Set<String> set = new HashSet<>();

        for(AfnPeriod afn:afnCount){
            set.add(afn.getPeriod());
            if (!afnListByTime.containsKey(afn.getPeriod())){
                afnListByTime.put(afn.getPeriod(), new LinkedList<AfnPeriod>());
                afnListByTime.get(afn.getPeriod()).add(afn);
            }
            else afnListByTime.get(afn.getPeriod()).add(afn);
        }
        //对timelist做一个排序
        List<String> list = new ArrayList<>(set);
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int mark = 1;
                try {
                    Date date1 = timeFormat.parse(o1);
                    Date date2 = timeFormat.parse(o2);
                    if(date1.getTime() < date2.getTime()){
                        mark = -1;//调整顺序,-1为不需要调整顺序;
                    }
                    if(o1.equals(o2)){
                        mark =  0;
                    }
                } catch (ParseException e) {
                    log.error("日期转换异常", e);
                    e.printStackTrace();
                }
                return mark;
            }

        });
        for (String time : list) {
            Set<Integer> temp = new HashSet<>();
            for (Integer t : afnSet) {
                temp.add(t);
            }
            List<AfnPeriod> afnPeriodList = afnListByTime.get(time);
            for (AfnPeriod afnPeriod : afnPeriodList) {
                afnMap.get(afnPeriod.getAfn()).add(afnPeriod.getCount());
                temp.remove(afnPeriod.getAfn());
            }
            for (Integer afn : temp) {
                afnMap.get(afn).add(0);
            }
        }

        AfnTimeVo afnTimeVo = new AfnTimeVo();
        afnTimeVo.setTimeList(list);
        afnTimeVo.setAfnHashMap(afnMap);
        return afnTimeVo;
    }

    // 分页查询5s内不合规packet记录明细
    @Override
    public PageInfo<PacketDetailsVo> getUnqualifiedDetails(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<PacketDetailsVo> packetDetailsVoList = packetCheckMapper.getUnqualifiedDetails();
        return new PageInfo<>(packetDetailsVoList);
    }
//
//
//    // 每隔2秒查询不同的合格包数目
//    @Override
//    public AfnTimeVo getDiffrentAfnCount(Integer second) {
//
//        // 1.查出数据库中所有afn
//        List<Integer> afnList = packetCheckMapper.getAfnList(second);
//
//        // 2.查出时间列表放入响应体
//        List<String> timeList = packetCheckMapper.getTimeList(second);
//
//        // 3.查出每个afn在上述时间的值列表，放入哈希表中key为afn的value中
//        Map<Integer, List<Integer>> afnHashMap = new HashMap<>();
//        // 4.查询最近时间段内不同afn数目总体统计
//        Map<Integer, Integer> afnTotalHashMap = new HashMap<>();
//        for(Integer afnSingle : afnList) {
//            List<Integer> afnCountList = packetCheckMapper.getAnfCountList(afnSingle,second);
//            if (afnCountList.size() < timeList.size()) {
//                afnCountList.clear();
//                for (String timeSingle :timeList) {
//                    Afn afnTemp = new Afn();
//                    int flag = packetCheckMapper.countNonNull(timeSingle,afnSingle,second);
//                    if (flag != 0) {
//                        afnTemp = packetCheckMapper.getSpecialCountList(timeSingle,afnSingle,second);
//                    }else {
//                        afnTemp.setAfn(afnSingle);
//                        afnTemp.setCountNum(0);
//                        afnTemp.setCnt(0);
//                    }
////                    afnTemp = packetCheckMapper.getSpecialCountList(timeSingle,afnSingle,second);
//                    afnCountList.add(afnTemp.getCnt());
//                }
//            }
//            afnHashMap.put(afnSingle,afnCountList);
//
//            int afnTotalCount = packetCheckMapper.getAfnTotalCount(afnSingle,second);
//            afnTotalHashMap.put(afnSingle,afnTotalCount);
//
//        }
//        // 5.将哈希表放入响应体
//        AfnTimeVo afnTimeVo = new AfnTimeVo();
//        afnTimeVo.setTimeList(timeList);
//        afnTimeVo.setAfnHashMap(afnHashMap);
//        afnTimeVo.setAfnTotalHashMap(afnTotalHashMap);
//
//        // 返回响应体
//        return afnTimeVo;
//    }
//
//    @Override
//    public AfnTimeVo getDifAfnCount(Integer second) {
//        List<AfnPeriod> afnCount = packetCheckMapper.getDifAfnCount(second);
//        //使用set对日期进行去重，然后再转换为list
//        Set<String> set = new HashSet<>();
//        Map<Integer, List<Integer>> map = new HashMap<>();
//        for(AfnPeriod afn:afnCount){
//            set.add(afn.getPeriod());
//            if (map.containsKey(afn.getAfn())) {
//                map.get(afn.getAfn()).add(afn.getCount());
//            } else {
//                map.put(afn.getAfn(), new LinkedList<Integer>());
//                map.get(afn.getAfn()).add(afn.getCount());
//            }
//        }
//        List<String> list = new ArrayList<>(set);
//        //对timelist做一个排序
//        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Collections.sort(list, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                int mark = 1;
//                try {
//                    Date date1 = timeFormat.parse(o1);
//                    Date date2 = timeFormat.parse(o2);
//                    if(date1.getTime() < date2.getTime()){
//                        mark = -1;//调整顺序,-1为不需要调整顺序;
//                    }
//                    if(o1.equals(o2)){
//                        mark =  0;
//                    }
//                } catch (ParseException e) {
//                    log.error("日期转换异常", e);
//                    e.printStackTrace();
//                }
//                return mark;
//            }
//
//        });
//        AfnTimeVo afnTimeVo = new AfnTimeVo();
//        afnTimeVo.setTimeList(list);
//        afnTimeVo.setAfnHashMap(map);
//        return afnTimeVo;
//    }
//
//    // 分页查询5s内不合规packet记录明细
//    @Override
//    public PageInfo<PacketDetailsVo> getUnqualifiedDetails(Integer page,Integer pageSize) {
//        PageHelper.startPage(page, pageSize);
//        List<PacketDetailsVo> packetDetailsVoList = packetCheckMapper.getUnqualifiedDetails();
//        PageInfo<PacketDetailsVo> res = new PageInfo<>(packetDetailsVoList);
//        return res;
//    }
}
