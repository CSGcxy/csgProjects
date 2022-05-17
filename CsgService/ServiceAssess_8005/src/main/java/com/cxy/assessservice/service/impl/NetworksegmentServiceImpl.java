package com.cxy.assessservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cxy.assessservice.entity.Networksegment;
import com.cxy.assessservice.entity.SafetyAssess;
import com.cxy.assessservice.entity.vo.SegAllScore;
import com.cxy.assessservice.entity.vo.SegScoreEntityVo;
import com.cxy.assessservice.entity.vo.TerminalScoreEntityVo;
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
    public List<List<SegScoreEntityVo>> getAllSegScoreDetails() {

        List<List<SegScoreEntityVo>> fiveTimeRangeScore = new ArrayList<>();

        for (int k = 1;k < 11;k++) {

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
                UpRateRatio upRateRatio = netSegMappper.getuprateRatio('"' + segList.get(i) + '"',k);  // 获取上行速率/平均速率
                // 查询结果为空则跳过
                if(upRateRatio == null) continue;

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
                DownRateRatio downRateRatio = netSegMappper.getdownrateRatio('"' + segList.get(i) + '"',k);  // 获取上行速率/平均速率
                // 查询结果为空则跳过
                if(downRateRatio == null) continue;

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
                OnlineCountRatio onlineCountRatio = netSegMappper.getonlineCountRatio('"' + segList.get(i) + '"',k);  // 获取上行速率/平均速率

                // 查询结果为空则跳过
                if(onlineCountRatio == null) continue;

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
                OfflineCountRatio offlineCountRatio = netSegMappper.getofflineCountRatio('"' + segList.get(i) + '"',k);  // 获取上行速率/平均速率

                // 查询结果为空则跳过
                if(offlineCountRatio == null) continue;

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
                AlertFlowCountRatio alertFlowCountRatio = netSegMappper.getAlertCountRatio('"' + segList.get(i) + '"',k);  // 获取上行速率/平均速率

                // 查询结果为空则跳过
                if(alertFlowCountRatio == null) continue;

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

            fiveTimeRangeScore.add(segScoreEntityVoList);
        }

        return fiveTimeRangeScore;
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
    public List<TerminalScoreEntityVo> getTerminalScoreDetails(List<List<SegScoreEntityVo>> segScoreEntityVoList) {
        // 用于最终结果返回
        List<TerminalScoreEntityVo> terminalScoreEntityVoList = new ArrayList<>();

        // 查找最近5s*10=60s范围内哪个5s的间段出现的网段数目多
        int mIndex = 0;
        int mLength = 0;
        for (int q = 0;q < segScoreEntityVoList.size();q++) {
            // 如果当前时间段的长度(出现的网段个数) > 目前出现的最大的网段个数
            if (segScoreEntityVoList.get(q).size() > mLength) {
                mIndex = q;  // 更新出现网段个数最大的时间段的索引值 为 当前时间段的索引值
                mLength = Integer.max(mLength,segScoreEntityVoList.get(q).size());
            }
        }

        // 查询比值倒数前10的各网段下的终端详情
        List<TerminalTotalRateRatio> terminalTotalRateRatioList = new ArrayList<>();
        for (int i = 0;i < segScoreEntityVoList.get(mIndex).size();i++) {
            // 先判断该网段最近5s*10=60s有没有数据 没有则跳过
            if (segScoreEntityVoList.get(0).isEmpty()) continue;

            // 获取总速率/总速率的平均速率  get(0)是在查最近5s对应的List  get(mIndex)是在查最近5s*mIndex对应的List(它下面网段最多) get(i)是在查该List下第i个网段的详细信息
            List<TerminalTotalRateRatio> segmentTerminalsDetails = netSegMappper.getTerminalTotalRateRatio(segScoreEntityVoList.get(mIndex).get(i).getSegment());
            for (TerminalTotalRateRatio terminalTotalRateRatio : segmentTerminalsDetails) {
                // 把网段名set进去
                terminalTotalRateRatio.setSegment(segScoreEntityVoList.get(mIndex).get(i).getSegment());
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

        return terminalScoreEntityVoList;
    }

}
