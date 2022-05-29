package com.cxy.assessservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cxy.assessservice.entity.Networksegment;
import com.cxy.assessservice.entity.vo.*;
import com.cxy.assessservice.entity.vo.ratioEntity.*;
import com.cxy.assessservice.mapper.NetworksegmentMapper;
import com.cxy.assessservice.service.NetworksegmentService;
import com.cxy.assessservice.utils.Entroy;
import com.cxy.assessservice.utils.ListPage;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@Service
@Slf4j
public class NetworksegmentServiceImpl extends ServiceImpl<NetworksegmentMapper, Networksegment> implements NetworksegmentService {

    @Autowired
    private NetworksegmentMapper netSegMappper;


    @Override
    public SegScoreAllTimeVo getAllSegScoreDetails() throws ParseException {

        List<List<SegScoreEntityVo>> timeRangeScore = new ArrayList<>();

        // 查询最近5s*10=50s的时间数组
        List<String> timeArray = netSegMappper.getTimeArray();
        System.out.println("最早时间为" + timeArray.get(0));
        // 取出该数组中最早的时间
        String earliestTimestr = timeArray.get(0);
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long earliestTime = df.parse(earliestTimestr).getTime();
        System.out.println("---------最早时间：" + earliestTime);

        // 查询10分钟内出现的网段
        List<String> segList = netSegMappper.getSegList();

        // 遍历各网段
        for (int i = 0;i < segList.size();i++) {

            List<SegScoreEntityVo> segScoreEntityVoList = new ArrayList<>();

            List<UpRateRatio> upRateRatioList = new ArrayList<>(); // 获取上行速率/平均速率List
            List<DownRateRatio> downRateRatioList = new ArrayList<>();;  // 获取上行速率/平均速率List
            List<OnlineCountRatio> onlineCountRatioList = new ArrayList<>();;  // 获取上行速率/平均速率List
            List<OfflineCountRatio> offlineCountRatioList = new ArrayList<>();;  // 获取上行速率/平均速率List
            List<AlertFlowCountRatio> alertFlowCountRatioList = new ArrayList<>();;  // 获取上行速率/平均速率List

            /**
             * 统计  上行速率  指标打分
             */
            upRateRatioList = netSegMappper.getuprateRatio('"' + segList.get(i) + '"',earliestTime);  // 获取上行速率/平均速率
            /**
             * 统计  下行速率  指标打分
             */
            downRateRatioList = netSegMappper.getdownrateRatio('"' + segList.get(i) + '"',earliestTime);  // 获取上行速率/平均速率
            /**
             * 统计  在线终端数  指标打分
             */
            onlineCountRatioList = netSegMappper.getonlineCountRatio('"' + segList.get(i) + '"',earliestTime);  // 获取上行速率/平均速率
            /**
             * 统计  离线终端数  指标打分
             */
            offlineCountRatioList = netSegMappper.getofflineCountRatio('"' + segList.get(i) + '"',earliestTime);  // 获取上行速率/平均速率
            /**
             * 统计  告警流数  指标打分
             */
            alertFlowCountRatioList = netSegMappper.getAlertCountRatio('"' + segList.get(i) + '"',earliestTime);  // 获取上行速率/平均速率


            System.out.println(upRateRatioList);

            // 返回8个时间段的数据
            for (int k = 0;k < 8;k++) {
                // new 一个返回对象 存储网段的名称 总分 各项分
                SegScoreEntityVo segScoreEntityVo = new SegScoreEntityVo();
                segScoreEntityVo.setSegment(segList.get(i)); // 赋值网段名
                SegAllScore segAllScore = new SegAllScore();


                System.out.println("查看本次遍历的时间" + timeArray.get(k));
                int curIndex = 10;
                for (int v = 0;v < upRateRatioList.size();v++) {
                    if (upRateRatioList.get(v).getTs().equals(timeArray.get(k))) {
                        curIndex = v;
                        break;
                    }
                }

                if (curIndex != 10) {
                    System.out.println("查看本次查询到的时间" + upRateRatioList.get(curIndex).getTs());
                }

                System.out.println("查看" + curIndex);

                if (curIndex != 10) {

                    /**
                     * 统计  上行速率  指标打分
                     */
//                UpRateRatio upRateRatio = netSegMappper.getuprateRatio('"' + segList.get(i) + '"',8-k-1);  // 获取上行速率/平均速率

                    // 查询结果为空则设置为0
                    // 根据速率判定分数
                    if (upRateRatioList.get(curIndex) == null) {
                        segAllScore.setUprateScore(0.0);
                    }else if (upRateRatioList.get(curIndex).getRatio() >= 1) {  // 速率比平均速率大
                        Double uprateScore = 80.0 + 20*((upRateRatioList.get(curIndex).getUprate()-upRateRatioList.get(curIndex).getAverageRate())/(upRateRatioList.get(curIndex).getMaxRate()-upRateRatioList.get(curIndex).getAverageRate()));
                        segAllScore.setUprateScore(uprateScore);
                    }else if (upRateRatioList.get(curIndex).getRatio() >= 0.5) {  // 速率小于平均速率 大于1/2平均速率
                        Double uprateScore = 80.0 - 80*((upRateRatioList.get(curIndex).getAverageRate()-upRateRatioList.get(curIndex).getUprate())/upRateRatioList.get(curIndex).getAverageRate());
                        segAllScore.setUprateScore(uprateScore);
                    }else {
                        segAllScore.setUprateScore(40.0);
                    }


                    /**
                     * 统计  下行速率  指标打分
                     */
//                DownRateRatio downRateRatio = netSegMappper.getdownrateRatio('"' + segList.get(i) + '"',8-k-1);  // 获取上行速率/平均速率

                    // 根据速率判定分数
                    if (downRateRatioList.get(curIndex) == null) {
                        segAllScore.setDownrateScore(0.0);
                    }else if (downRateRatioList.get(curIndex).getRatio() >= 1) {  // 速率比平均速率大
                        Double downrateScore = 80.0 + 20*((downRateRatioList.get(curIndex).getDownrate()-downRateRatioList.get(curIndex).getAverageRate())/(downRateRatioList.get(curIndex).getMaxRate()-downRateRatioList.get(curIndex).getAverageRate()));
                        segAllScore.setDownrateScore(downrateScore);
                    }else if (downRateRatioList.get(curIndex).getRatio() >= 0.5) {  // 速率小于平均速率 大于1/2平均速率
                        Double downrateScore = 80.0 - 80*((downRateRatioList.get(curIndex).getAverageRate()-downRateRatioList.get(curIndex).getDownrate())/downRateRatioList.get(curIndex).getAverageRate());
                        segAllScore.setDownrateScore(downrateScore);
                    }else {
                        segAllScore.setDownrateScore(40.0);
                    }

                    /**
                     * 统计  在线终端数  指标打分
                     */
//                OnlineCountRatio onlineCountRatio = netSegMappper.getonlineCountRatio('"' + segList.get(i) + '"',8-k-1);  // 获取上行速率/平均速率

                    // 根据速率判定分数
                    if (onlineCountRatioList.get(curIndex) == null) {
                        segAllScore.setOndevicecountScore(0.0);
                    }else if (onlineCountRatioList.get(curIndex).getRatio() >= 1) {  // 速率比平均速率大
                        Double onlineCountScore = 80.0 + 20*((onlineCountRatioList.get(curIndex).getOnlineCount()-onlineCountRatioList.get(curIndex).getAverageCount())/(onlineCountRatioList.get(curIndex).getMaxCount()-onlineCountRatioList.get(curIndex).getAverageCount()));
                        segAllScore.setOndevicecountScore(onlineCountScore);
                    }else if (onlineCountRatioList.get(curIndex).getRatio() >= 0.5) {  // 速率小于平均速率 大于1/2平均速率
                        Double onlineCountScore = 80.0 - 80*((onlineCountRatioList.get(curIndex).getAverageCount()-onlineCountRatioList.get(curIndex).getOnlineCount())/onlineCountRatioList.get(curIndex).getAverageCount());
                        segAllScore.setOndevicecountScore(onlineCountScore);
                    }else {
                        segAllScore.setOndevicecountScore(40.0);
                    }


                    /**
                     * 统计  离线终端数  指标打分
                     */
//                OfflineCountRatio offlineCountRatio = netSegMappper.getofflineCountRatio('"' + segList.get(i) + '"',8-k-1);  // 获取上行速率/平均速率

                    // 根据速率判定分数
                    if (offlineCountRatioList.get(curIndex) == null) {
                        segAllScore.setOffdevicecountScore(0.0);
                    }else if (offlineCountRatioList.get(curIndex).getRatio() >= 1.5) {  // 速率比平均速率大
                        segAllScore.setOffdevicecountScore(40.0);
                    }else if (offlineCountRatioList.get(curIndex).getRatio() >= 1.3) {  // 速率小于平均速率 大于1/2平均速率
                        segAllScore.setOffdevicecountScore(60.0);
                    }else if (offlineCountRatioList.get(curIndex).getRatio() >= 1){
                        Double molecule = offlineCountRatioList.get(curIndex).getOfflineCount()-offlineCountRatioList.get(curIndex).getAverageCount();
                        Double denominator = offlineCountRatioList.get(curIndex).getMaxCount()-offlineCountRatioList.get(curIndex).getAverageCount();
                        Double offlineCountScore = 80.0 - 80*(molecule/denominator);
                        segAllScore.setOffdevicecountScore(offlineCountScore);
                    }else {
                        Double offlineCountScore = 80 + 20*((offlineCountRatioList.get(curIndex).getAverageCount()-offlineCountRatioList.get(curIndex).getOfflineCount())/offlineCountRatioList.get(curIndex).getAverageCount());
                        segAllScore.setOffdevicecountScore(offlineCountScore);
                    }

                    /**
                     * 统计  告警流数  指标打分
                     */
//                AlertFlowCountRatio alertFlowCountRatio = netSegMappper.getAlertCountRatio('"' + segList.get(i) + '"',8-k-1);  // 获取上行速率/平均速率

                    // 根据速率判定分数
                    if (alertFlowCountRatioList.get(curIndex) == null) {
                        segAllScore.setAlertflowScore(0.0);
                    }else if (alertFlowCountRatioList.get(curIndex).getAlertFlowCount() == 0) {  // 速率比平均速率大
                        segAllScore.setAlertflowScore(95.0);
                    }else {
                        Double alertCountScore = 80.0 * Math.pow(Math.E, -alertFlowCountRatioList.get(curIndex).getAlertFlowCount());
                        segAllScore.setAlertflowScore(alertCountScore);
                    }

                    if (segAllScore.getUprateScore().equals(0.0)) {
                        segAllScore.setActiveflowScore(0.0);
                    }else {
                        segAllScore.setActiveflowScore(95.0);
                    }
                    segScoreEntityVo.setSegAllScore(segAllScore);

                }
                if(curIndex == 10) {
                    segAllScore.setUprateScore(0.0);
                    segAllScore.setDownrateScore(0.0);
                    segAllScore.setOndevicecountScore(0.0);
                    segAllScore.setOffdevicecountScore(0.0);
                    segAllScore.setAlertflowScore(0.0);
                    segAllScore.setActiveflowScore(0.0);
                    segScoreEntityVo.setSegAllScore(segAllScore);
                    System.out.println("走到这里了~");
                }

                /**
                 * 熵值法计算权重 确定总分
                 */
                if (segAllScore.getUprateScore().equals(0.0)) {
                    segScoreEntityVo.setTotalScore(0.0);
                }else {
                    List<Double> scoreList = new ArrayList<>();
                    scoreList.add(segAllScore.getUprateScore());
                    scoreList.add(segAllScore.getDownrateScore());
                    scoreList.add(segAllScore.getOndevicecountScore());
                    scoreList.add(segAllScore.getOffdevicecountScore());
                    scoreList.add(segAllScore.getAlertflowScore());
                    scoreList.add(segAllScore.getActiveflowScore());
                    Double totalScore = getTotalScore(scoreList);
                    segScoreEntityVo.setTotalScore(totalScore);
                }

                /**
                 * 统计  时刻  并记录返回
                 */
                if (segAllScore.getUprateScore().equals(0.0)) {
                    segScoreEntityVo.setTs(timeArray.get(k));
                }else {
//                    segScoreEntityVo.setTs(upRateRatio.getTs());
                    segScoreEntityVo.setTs(timeArray.get(k));
                }

                segScoreEntityVoList.add(segScoreEntityVo);
            }

//            if (segScoreEntityVoList.isEmpty()) {  // 当前时间段没有任何网段下有数据则跳过 不加入结果集
//                continue;
//            }else {  // 查询到数据则放入结果集
//                timeRangeScore.add(segScoreEntityVoList);
//            }
            timeRangeScore.add(segScoreEntityVoList);
        }

        List<List<Double>> segAllTimeScoreList = new ArrayList<>();
        List<SegSixScoreVo> latestTimeSegDeatils = new ArrayList<>();
        System.out.println("查看处理结果" + timeRangeScore);
        for(int h = 0;h < segList.size();h++) {
            // 将 各个网段  8个时间段内的总分list(几个网段就有几个list)  分别set进segAllTimeScoreList
            List<Double> seglist = new ArrayList<>();
            for (int u = 0;u < timeRangeScore.get(h).size();u++) {
                seglist.add(timeRangeScore.get(h).get(u).getTotalScore());
            }
            segAllTimeScoreList.add(seglist);
        }

        List<String> quotaList = new ArrayList<>();
        quotaList.add("LT4G");
        quotaList.add("Others");
        quotaList.add("PW");
        quotaList.add("WX230");
        quotaList.add("YD4G");
        quotaList.add("YD5G");
        quotaList.add("YDWLW");
        quotaList.add("ZZ");

        for (int e = 0;e < 6;e++) {
            // 将最新时间段的各个网段打分详情返回
//            List<SegSixScoreVo> latestlist = new ArrayList<>();
            SegSixScoreVo segSixScoreVo = new SegSixScoreVo();
            if (e == 0) {
                for (int p1 = 0;p1 < segList.size();p1++) {
                    if ("LT4G".equals(segList.get(p1))) {
                        segSixScoreVo.setLT4G(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                    if ("Others".equals(segList.get(p1))) {
                        segSixScoreVo.setOthers(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                    if ("PW".equals(segList.get(p1))) {
                        segSixScoreVo.setPW(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                    if ("WX230".equals(segList.get(p1))) {
                        segSixScoreVo.setWX230(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                    if ("YD4G".equals(segList.get(p1))) {
                        segSixScoreVo.setYD4G(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                    if ("YD5G".equals(segList.get(p1))) {
                        segSixScoreVo.setYD5G(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                    if ("YDWLW".equals(segList.get(p1))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                    if ("ZZ".equals(segList.get(p1))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p1).get(timeRangeScore.get(p1).size()-1).getSegAllScore().getUprateScore());
                    }
                }
            }

            if (e == 1) {
                for (int p2 = 0;p2 < segList.size();p2++) {
                    if ("LT4G".equals(segList.get(p2))) {
                        segSixScoreVo.setLT4G(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                    if ("Others".equals(segList.get(p2))) {
                        segSixScoreVo.setOthers(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                    if ("PW".equals(segList.get(p2))) {
                        segSixScoreVo.setPW(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                    if ("WX230".equals(segList.get(p2))) {
                        segSixScoreVo.setWX230(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                    if ("YD4G".equals(segList.get(p2))) {
                        segSixScoreVo.setYD4G(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                    if ("YD5G".equals(segList.get(p2))) {
                        segSixScoreVo.setYD5G(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                    if ("YDWLW".equals(segList.get(p2))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                    if ("ZZ".equals(segList.get(p2))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p2).get(timeRangeScore.get(p2).size()-1).getSegAllScore().getDownrateScore());
                    }
                }
            }

            if (e == 2) {
                for (int p3 = 0;p3 < segList.size();p3++) {
                    if ("LT4G".equals(segList.get(p3))) {
                        segSixScoreVo.setLT4G(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                    if ("Others".equals(segList.get(p3))) {
                        segSixScoreVo.setOthers(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                    if ("PW".equals(segList.get(p3))) {
                        segSixScoreVo.setPW(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                    if ("WX230".equals(segList.get(p3))) {
                        segSixScoreVo.setWX230(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                    if ("YD4G".equals(segList.get(p3))) {
                        segSixScoreVo.setYD4G(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                    if ("YD5G".equals(segList.get(p3))) {
                        segSixScoreVo.setYD5G(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                    if ("YDWLW".equals(segList.get(p3))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                    if ("ZZ".equals(segList.get(p3))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p3).get(timeRangeScore.get(p3).size()-1).getSegAllScore().getOndevicecountScore());
                    }
                }
            }

            if (e == 3) {
                for (int p4 = 0;p4 < segList.size();p4++) {
                    if ("LT4G".equals(segList.get(p4))) {
                        segSixScoreVo.setLT4G(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                    if ("Others".equals(segList.get(p4))) {
                        segSixScoreVo.setOthers(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                    if ("PW".equals(segList.get(p4))) {
                        segSixScoreVo.setPW(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                    if ("WX230".equals(segList.get(p4))) {
                        segSixScoreVo.setWX230(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                    if ("YD4G".equals(segList.get(p4))) {
                        segSixScoreVo.setYD4G(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                    if ("YD5G".equals(segList.get(p4))) {
                        segSixScoreVo.setYD5G(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                    if ("YDWLW".equals(segList.get(p4))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                    if ("ZZ".equals(segList.get(p4))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p4).get(timeRangeScore.get(p4).size()-1).getSegAllScore().getOffdevicecountScore());
                    }
                }
            }

            if (e == 4) {
                for (int p5 = 0;p5 < segList.size();p5++) {
                    if ("LT4G".equals(segList.get(p5))) {
                        segSixScoreVo.setLT4G(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                    if ("Others".equals(segList.get(p5))) {
                        segSixScoreVo.setOthers(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                    if ("PW".equals(segList.get(p5))) {
                        segSixScoreVo.setPW(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                    if ("WX230".equals(segList.get(p5))) {
                        segSixScoreVo.setWX230(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                    if ("YD4G".equals(segList.get(p5))) {
                        segSixScoreVo.setYD4G(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                    if ("YD5G".equals(segList.get(p5))) {
                        segSixScoreVo.setYD5G(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                    if ("YDWLW".equals(segList.get(p5))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                    if ("ZZ".equals(segList.get(p5))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p5).get(timeRangeScore.get(p5).size()-1).getSegAllScore().getAlertflowScore());
                    }
                }
            }

            if (e == 5) {
                for (int p6 = 0;p6 < segList.size();p6++) {
                    if ("LT4G".equals(segList.get(p6))) {
                        segSixScoreVo.setLT4G(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                    if ("Others".equals(segList.get(p6))) {
                        segSixScoreVo.setOthers(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                    if ("PW".equals(segList.get(p6))) {
                        segSixScoreVo.setPW(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                    if ("WX230".equals(segList.get(p6))) {
                        segSixScoreVo.setWX230(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                    if ("YD4G".equals(segList.get(p6))) {
                        segSixScoreVo.setYD4G(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                    if ("YD5G".equals(segList.get(p6))) {
                        segSixScoreVo.setYD5G(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                    if ("YDWLW".equals(segList.get(p6))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                    if ("ZZ".equals(segList.get(p6))) {
                        segSixScoreVo.setYDWLW(timeRangeScore.get(p6).get(timeRangeScore.get(p6).size()-1).getSegAllScore().getActiveflowScore());
                    }
                }
            }

            quotaList.add("LT4G");
            quotaList.add("Others");
            quotaList.add("PW");
            quotaList.add("WX230");
            quotaList.add("YD4G");
            quotaList.add("YD5G");
            quotaList.add("YDWLW");
            quotaList.add("ZZ");


            if (segSixScoreVo.getLT4G() == null) {
                segSixScoreVo.setLT4G(0.0);
            }
            if (segSixScoreVo.getOthers() == null) {
                segSixScoreVo.setOthers(0.0);
            }
            if (segSixScoreVo.getPW() == null) {
                segSixScoreVo.setPW(0.0);
            }
            if (segSixScoreVo.getWX230() == null) {
                segSixScoreVo.setWX230(0.0);
            }
            if (segSixScoreVo.getYD4G() == null) {
                segSixScoreVo.setYD4G(0.0);
            }
            if (segSixScoreVo.getYD5G() == null) {
                segSixScoreVo.setYD5G(0.0);
            }
            if (segSixScoreVo.getYDWLW() == null) {
                segSixScoreVo.setYDWLW(0.0);
            }
            if (segSixScoreVo.getZZ() == null) {
                segSixScoreVo.setZZ(0.0);
            }

//            latestlist.add(segSixScoreVo);

//            segAllScore.setUprateScore(timeRangeScore.get(h).get(timeRangeScore.get(h).size()-1).getSegAllScore().getUprateScore());
//            segAllScore.setDownrateScore(timeRangeScore.get(h).get(timeRangeScore.get(h).size()-1).getSegAllScore().getDownrateScore());
//            segAllScore.setOndevicecountScore(timeRangeScore.get(h).get(timeRangeScore.get(h).size()-1).getSegAllScore().getOndevicecountScore());
//            segAllScore.setOffdevicecountScore(timeRangeScore.get(h).get(timeRangeScore.get(h).size()-1).getSegAllScore().getOffdevicecountScore());
//            segAllScore.setAlertflowScore(timeRangeScore.get(h).get(timeRangeScore.get(h).size()-1).getSegAllScore().getAlertflowScore());
//            segAllScore.setActiveflowScore(timeRangeScore.get(h).get(timeRangeScore.get(h).size()-1).getSegAllScore().getActiveflowScore());

//            latestlist.add(segAllScore);

            latestTimeSegDeatils.add(segSixScoreVo);
        }


        SegScoreAllTimeVo segScoreAllTimeVo = new SegScoreAllTimeVo();
        segScoreAllTimeVo.setTimeRangeScore(timeRangeScore);
//        Collections.reverse(timeArray);  // 时间数组是从小到大排列的 转为从大到小排列
        segScoreAllTimeVo.setTimeArray(timeArray);
        segScoreAllTimeVo.setSegmentList(segList);
        segScoreAllTimeVo.setSegAllTimeScoreList(segAllTimeScoreList);
        segScoreAllTimeVo.setLatestTimeSegDeatils(latestTimeSegDeatils);

        return segScoreAllTimeVo;
    }


    public Double getTotalScore(List<Double> scoreList) {

        // 取出6个指标的6次取值放入二维数组quotaScoreList
        Double[][] arrays = new Double[6][6];

        // 下面对array的操作会影响array的值，所以复制一份用于下面计算每个指标的6次取值的平均值
        Double[][] arraysCopy = new Double[6][6];
        for (int i = 0;i < 6;i++) {
            for (int j = 0;j < 6;j++) {
                arraysCopy[i][j] = scoreList.get(j);
                arrays[i][j] = scoreList.get(j);
            }
        }

        // 开始计算权重
        List<List<Double>> list = new ArrayList<>();
        for (Double[] array : arrays) {
            list.add(Arrays.asList(array));
        }
        List<Double> weight = Entroy.getWeight(list);  // weight存放权重值

        // 计算平均值
        double[] average = new double[6];
        for (int l = 0;l < 6;l++) {
            int sum = 0;
            for (int i = 0;i < 6;i++) {
                sum += arraysCopy[i][l];
            }
            average[l] = sum / 6;
        }

        Double result = 0.0;  // 计算总分，用指标1的6次取值的平均值 * 指标1的权重 + 指标2的6次取值的平均值 * 指标2的权重 + ...
        for (int k = 0;k < 6;k++) {
            result += weight.get(k) * average[k];
        }
        return result;
    }

    @Override
    public PageInfoVo getTerminalScoreDetails(SegScoreAllTimeVo segScoreEntityVoList,Integer pageNum,Integer pageSize) {

        // 用于最终结果返回
        List<TerminalScoreEntityVo> terminalScoreEntityVoList = new ArrayList<>();

        // 查找最近5s*10=60s范围内哪个5s的间段出现的网段数目多
        int mIndex = 0;
        int mLength = 0;
        for (int q = 0;q < segScoreEntityVoList.getTimeRangeScore().size();q++) {
            // 如果当前时间段的长度(出现的网段个数) > 目前出现的最大的网段个数
            if (segScoreEntityVoList.getTimeRangeScore().get(q).size() > mLength) {
                mIndex = q;  // 更新出现网段个数最大的时间段的索引值 为 当前时间段的索引值
                mLength = Integer.max(mLength,segScoreEntityVoList.getTimeRangeScore().get(q).size());
            }
        }

        // 查询比值倒数前10的各网段下的终端详情
        List<TerminalTotalRateRatio> terminalTotalRateRatioList = new ArrayList<>();
        for (int i = 0;i < segScoreEntityVoList.getTimeRangeScore().get(mIndex).size();i++) {
            // 先判断该网段最近5s*10=60s有没有数据 没有则跳过
            if (segScoreEntityVoList.getTimeRangeScore().get(0).isEmpty()) continue;

            // 获取总速率/总速率的平均速率  get(0)是在查最近5s对应的List  get(mIndex)是在查最近5s*mIndex对应的List(它下面网段最多) get(i)是在查该List下第i个网段的详细信息
            List<TerminalTotalRateRatio> segmentTerminalsDetails = netSegMappper.getTerminalTotalRateRatio(segScoreEntityVoList.getTimeRangeScore().get(mIndex).get(i).getSegment());
            for (TerminalTotalRateRatio terminalTotalRateRatio : segmentTerminalsDetails) {
                // 把网段名set进去
                terminalTotalRateRatio.setSegment(segScoreEntityVoList.getTimeRangeScore().get(mIndex).get(i).getSegment());
                terminalTotalRateRatioList.add(terminalTotalRateRatio);
            }
        }

        for (int i = 0;i < terminalTotalRateRatioList.size();i++) {
            // 用于封装每一个终端的详情
            TerminalScoreEntityVo terminalScoreEntityVo = new TerminalScoreEntityVo();

            // 根据速率判定分数 封装终端总速率评分
            if (terminalTotalRateRatioList.get(i).getRatio() >= 1) {  // 速率比平均速率大
                Double totalrateScore = 80.0 + 20*((terminalTotalRateRatioList.get(i).getTotalrate()-terminalTotalRateRatioList.get(i).getAverageRate())/(terminalTotalRateRatioList.get(i).getMaxRate()-terminalTotalRateRatioList.get(i).getAverageRate()));
                terminalScoreEntityVo.setTotalrateScore(totalrateScore);
            }else if (terminalTotalRateRatioList.get(i).getRatio() >= 0.5) {  // 速率小于平均速率 大于1/2平均速率
                Double totalrateScore = 80.0 - 80*((terminalTotalRateRatioList.get(i).getAverageRate()-terminalTotalRateRatioList.get(i).getUprate())/terminalTotalRateRatioList.get(i).getAverageRate());
                terminalScoreEntityVo.setTotalrateScore(totalrateScore);
            }else {
                terminalScoreEntityVo.setTotalrateScore(40.0);
            }

            // 封装终端详情信息
            terminalScoreEntityVo.setIp(terminalTotalRateRatioList.get(i).getIp());
            terminalScoreEntityVo.setTs(terminalTotalRateRatioList.get(i).getTs());
            terminalScoreEntityVo.setLocation(terminalTotalRateRatioList.get(i).getLocation());
            terminalScoreEntityVo.setUprate(terminalTotalRateRatioList.get(i).getUprate());
            terminalScoreEntityVo.setDownrate(terminalTotalRateRatioList.get(i).getDownrate());
            terminalScoreEntityVo.setTotalrate(terminalTotalRateRatioList.get(i).getTotalrate());
            terminalScoreEntityVo.setSegment(terminalTotalRateRatioList.get(i).getSegment());

            // 将封装结果set进返回对象
            terminalScoreEntityVoList.add(terminalScoreEntityVo);
        }

        // 截取分页查询的结果并封装
        List<TerminalScoreEntityVo> terminalScoreEntityVoPage = ListPage.startPage(terminalScoreEntityVoList,pageNum,pageSize);

        // 封装分页查询结果并返回查询总数(是总记录数 不是分页查询的记录数)
        PageInfoVo pageInfoVo = new PageInfoVo();
        pageInfoVo.setTerminalScoreEntityVoPage(terminalScoreEntityVoPage);
        pageInfoVo.setTotalNum(terminalScoreEntityVoList.size());
        pageInfoVo.setPageNum(pageNum);
        pageInfoVo.setPageSize(pageSize);
        pageInfoVo.setCurPageSize(terminalScoreEntityVoPage.size());
        pageInfoVo.setStartRow(pageSize*(pageNum-1) + 1);
        pageInfoVo.setEndRow(pageSize*(pageNum));
        pageInfoVo.setTotalPageNum(pageInfoVo.getTotalNum()/pageInfoVo.getPageSize()+1);

        return pageInfoVo;
    }
}
