package com.cxy.assessservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cxy.assessservice.entity.Networksegment;
import com.cxy.assessservice.entity.SafetyAssess;
import com.cxy.assessservice.entity.vo.SegAllScore;
import com.cxy.assessservice.entity.vo.SegScoreEntityVo;
import com.cxy.assessservice.entity.vo.ratioEntity.*;
import com.cxy.assessservice.mapper.NetworksegmentMapper;
import com.cxy.assessservice.service.NetworksegmentService;
import com.cxy.assessservice.utils.Entroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<SegScoreEntityVo> getAllSegScoreDetails() {
        List<SegScoreEntityVo> segScoreEntityVoList = new ArrayList<>();
        List<String> segList = netSegMappper.getSegList();
        // 遍历各网段
        for (int i = 0;i < segList.size();i++) {
            // new 一个返回对象 存储网段的名称 总分 各项分
            SegScoreEntityVo segScoreEntityVo = new SegScoreEntityVo();
            segScoreEntityVo.setSegment(segList.get(i)); // 赋值网段名
            SegAllScore segAllScore = new SegAllScore();

            /**
             * 统计  上行速率  指标打分
             */
            UpRateRatio upRateRatio = netSegMappper.getuprateRatio('"' + segList.get(i) + '"');  // 获取上行速率/平均速率
            // 根据速率判定分数
            if (upRateRatio.getRatio() >= 1) {  // 速率比平均速率大
                Double uprateScore = 80.0 + 20*((upRateRatio.getUprate()-upRateRatio.getAverageRate())/(upRateRatio.getMaxRate()-upRateRatio.getAverageRate()));
                segAllScore.setUprateScore(uprateScore);
            }else if (upRateRatio.getRatio() >= 0.5) {  // 速率小于平均速率 大于1/2平均速率
                Double uprateScore = 80.0 - 80*((upRateRatio.getAverageRate()-upRateRatio.getUprate())/upRateRatio.getAverageRate());
                segAllScore.setUprateScore(uprateScore);
            }else {
                segAllScore.setUprateScore(40.0);
            }


            /**
             * 统计  下行速率  指标打分
             */
            DownRateRatio downRateRatio = netSegMappper.getdownrateRatio('"' + segList.get(i) + '"');  // 获取上行速率/平均速率
            // 根据速率判定分数
            if (downRateRatio.getRatio() >= 1) {  // 速率比平均速率大
                Double downrateScore = 80.0 + 20*((downRateRatio.getDownrate()-downRateRatio.getAverageRate())/(downRateRatio.getMaxRate()-downRateRatio.getAverageRate()));
                segAllScore.setDownrateScore(downrateScore);
            }else if (downRateRatio.getRatio() >= 0.5) {  // 速率小于平均速率 大于1/2平均速率
                Double downrateScore = 80.0 - 80*((downRateRatio.getAverageRate()-downRateRatio.getDownrate())/downRateRatio.getAverageRate());
                segAllScore.setDownrateScore(downrateScore);
            }else {
                segAllScore.setDownrateScore(40.0);
            }

            /**
             * 统计  在线终端数  指标打分
             */
            OnlineCountRatio onlineCountRatio = netSegMappper.getonlineCountRatio('"' + segList.get(i) + '"');  // 获取上行速率/平均速率
            // 根据速率判定分数
            if (onlineCountRatio.getRatio() >= 1) {  // 速率比平均速率大
                Double onlineCountScore = 80.0 + 20*((onlineCountRatio.getOnlineCount()-onlineCountRatio.getAverageCount())/(onlineCountRatio.getMaxCount()-onlineCountRatio.getAverageCount()));
                segAllScore.setOndevicecountScore(onlineCountScore);
            }else if (onlineCountRatio.getRatio() >= 0.5) {  // 速率小于平均速率 大于1/2平均速率
                Double onlineCountScore = 80.0 - 80*((onlineCountRatio.getAverageCount()-onlineCountRatio.getOnlineCount())/onlineCountRatio.getAverageCount());
                segAllScore.setOndevicecountScore(onlineCountScore);
            }else {
                segAllScore.setOndevicecountScore(40.0);
            }


            /**
             * 统计  离线终端数  指标打分
             */
            OfflineCountRatio offlineCountRatio = netSegMappper.getofflineCountRatio('"' + segList.get(i) + '"');  // 获取上行速率/平均速率
            // 根据速率判定分数
            if (offlineCountRatio.getRatio() >= 1.5) {  // 速率比平均速率大
                segAllScore.setOffdevicecountScore(40.0);
            }else if (offlineCountRatio.getRatio() >= 1.3) {  // 速率小于平均速率 大于1/2平均速率
                segAllScore.setOffdevicecountScore(60.0);
            }else if (offlineCountRatio.getRatio() >= 1){
                Double molecule = offlineCountRatio.getOfflineCount()-offlineCountRatio.getAverageCount();
                Double denominator = offlineCountRatio.getMaxCount()-offlineCountRatio.getAverageCount();
                Double offlineCountScore = 80.0 - 80*(molecule/denominator);
                segAllScore.setOffdevicecountScore(offlineCountScore);
            }else {
                Double offlineCountScore = 80 + 20*((offlineCountRatio.getAverageCount()-offlineCountRatio.getOfflineCount())/offlineCountRatio.getAverageCount());
                segAllScore.setOffdevicecountScore(offlineCountScore);
            }

            /**
             * 统计  告警流数  指标打分
             */
            AlertFlowCountRatio alertFlowCountRatio = netSegMappper.getAlertCountRatio('"' + segList.get(i) + '"');  // 获取上行速率/平均速率
            // 根据速率判定分数
            if (alertFlowCountRatio.getAlertFlowCount() == 0) {  // 速率比平均速率大
                segAllScore.setAlertflowScore(95.0);
            }else {
                Double alertCountScore = 80.0 * Math.pow(Math.E, -alertFlowCountRatio.getAlertFlowCount());
                segAllScore.setAlertflowScore(alertCountScore);
            }

            segAllScore.setActiveflowScore(95.0);
            segScoreEntityVo.setSegAllScore(segAllScore);

            /**
             * 熵值法计算权重 确定总分
             */
            List<Double> scoreList = new ArrayList<>();
            scoreList.add(segAllScore.getUprateScore());
            scoreList.add(segAllScore.getDownrateScore());
            scoreList.add(segAllScore.getOndevicecountScore());
            scoreList.add(segAllScore.getOffdevicecountScore());
            scoreList.add(segAllScore.getAlertflowScore());
            scoreList.add(segAllScore.getActiveflowScore());
            Double totalScore = getTotalScore(scoreList);
            segScoreEntityVo.setTotalScore(totalScore);

            /**
             * 统计  时刻  并记录返回
             */
            segScoreEntityVo.setTs(upRateRatio.getTs());

            segScoreEntityVoList.add(segScoreEntityVo);
        }
        return segScoreEntityVoList;
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



}
