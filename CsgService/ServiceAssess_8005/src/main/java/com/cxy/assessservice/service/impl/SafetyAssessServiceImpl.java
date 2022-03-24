package com.cxy.assessservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.assessservice.entity.SafetyAssess;
import com.cxy.assessservice.mapper.SafetyAssessMapper;
import com.cxy.assessservice.service.SafetyAssessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.assessservice.utils.AHP;
import com.cxy.assessservice.utils.Entroy;
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
 * @since 2022-03-23
 */
@Service
public class SafetyAssessServiceImpl extends ServiceImpl<SafetyAssessMapper, SafetyAssess> implements SafetyAssessService {

    @Autowired
    SafetyAssessService safetyAssessService;

    @Override
    public List<SafetyAssess> getQuotaScore() {

//        List<SafetyAssess> QuotaScoreList = safetyAssessMapper.getQuotaScoreList();
//        return QuotaScoreList;
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.ge("ts",1);
        wrapper.le("ts",6);
        return safetyAssessService.list(wrapper);
    }

    @Override
    public Double getTotalScore() {

        // 取出6个指标的6次取值放入二维数组quotaScoreList
        Double[][] arrays = new Double[6][6];
        List<SafetyAssess> quotaScoreList = safetyAssessService.getQuotaScore();
        for (int i = 0;i < 6;i++) {
            arrays[i][0] = quotaScoreList.get(i).getScoreOne();
            arrays[i][1] = quotaScoreList.get(i).getScoreTwo();
            arrays[i][2] = quotaScoreList.get(i).getScoreThree();
            arrays[i][3] = quotaScoreList.get(i).getScoreFour();
            arrays[i][4] = quotaScoreList.get(i).getScoreFive();
            arrays[i][5] = quotaScoreList.get(i).getScoreSix();
        }

        // 下面对array的操作会影响array的值，所以复制一份用于下面计算每个指标的6次取值的平均值
        Double[][] arraysCopy = new Double[6][6];
        for (int i = 0;i < 6;i++) {
            for (int j = 0;j < 6;j++) {
                arraysCopy[i][j] = arrays[i][j];
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
    public Double getSubjectiveTotalScore() {
        // 取出6个指标的6次取值放入二维数组quotaScoreList
        Double[][] arrays = new Double[6][6];
        List<SafetyAssess> quotaScoreList = safetyAssessService.getQuotaScore();
        for (int i = 0;i < 6;i++) {
            arrays[i][0] = quotaScoreList.get(i).getScoreOne();
            arrays[i][1] = quotaScoreList.get(i).getScoreTwo();
            arrays[i][2] = quotaScoreList.get(i).getScoreThree();
            arrays[i][3] = quotaScoreList.get(i).getScoreFour();
            arrays[i][4] = quotaScoreList.get(i).getScoreFive();
            arrays[i][5] = quotaScoreList.get(i).getScoreSix();
        }

        // 下面对array的操作会影响array的值，所以复制一份用于下面计算每个指标的6次取值的平均值
        Double[][] arraysCopy = new Double[6][6];
        for (int i = 0;i < 6;i++) {
            for (int j = 0;j < 6;j++) {
                arraysCopy[i][j] = arrays[i][j];
            }
        }

        // 开始计算权重
        Double[] weight = AHP.getweight();  // weight存放权重值

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
            result += weight[k] * average[k];
        }
        return result;
    }


}
