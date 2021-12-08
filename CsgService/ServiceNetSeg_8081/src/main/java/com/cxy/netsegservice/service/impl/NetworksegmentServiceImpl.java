package com.cxy.netsegservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.baseService.handler.CsgException;
import com.cxy.netsegservice.entity.Location;
import com.cxy.netsegservice.entity.Networksegment;
import com.cxy.netsegservice.entity.TerminalTrend;
import com.cxy.netsegservice.entity.vo.*;
import com.cxy.netsegservice.mapper.NetworksegmentMapper;
import com.cxy.netsegservice.service.NetworksegmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@Service
public class NetworksegmentServiceImpl extends ServiceImpl<NetworksegmentMapper, Networksegment> implements NetworksegmentService {


    @Autowired
    private NetworksegmentMapper netSegMappper;

    //查询networkSegment表中的网段名
    @Override
    public NetSegVO selectNetSeg() {
        List<String> netSegList = netSegMappper.selectNetSeg();
        String pattern_10x = "10.*";
        List<String> segs10x = new ArrayList<>();
        List<String> segsElse = new ArrayList<>();
        for (String ele : netSegList) {
            if (Pattern.matches(pattern_10x, ele)) {
                segs10x.add(ele);
            } else {
                segsElse.add(ele);
            }
        }
        return new NetSegVO(segs10x, segsElse);
    }

    //查询出告警流数出现的时间、IP及对应的流数
    @Override
    public List<AlertFlowVO> getAlertFlow() {
        String format = "yyyy/MM/dd HH:mm:ss";
        QueryWrapper<Networksegment> netSegWrapper = new QueryWrapper<>();
        netSegWrapper.ge("Alert_flow", 0);
        netSegWrapper.orderByDesc("TIMESTAMP");
        netSegWrapper.last("limit 10");
        List<Networksegment> networksegments = baseMapper.selectList(netSegWrapper);
        if (networksegments == null) {
            throw new CsgException(20001, "无告警流");
        }
        List<AlertFlowVO> alertFlowVOList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        for (Networksegment netSeg : networksegments) {
            AlertFlowVO alertFlowVO = new AlertFlowVO();
            //因为数据库存储的时间戳不是以Date类型存储的，而是Integer类型，所以这里需要将其格式化
            alertFlowVO.setTimestamp(sdf.format(netSeg.getTimestamp()));
            alertFlowVO.setNetworkseg(netSeg.getNetworkseg());
            alertFlowVO.setAlertFlow(netSeg.getAlertFlow());
            alertFlowVOList.add(alertFlowVO);
        }

        return alertFlowVOList;
    }

    //根据传入的网段名，在networkSegment表中去查询总体统计表
    @Override
    public List<NetSegTotalVO> selectNetworkSegmentTerminalTotal(String segment) {
        return netSegMappper.selectNetworkSegmentTerminalTotal(segment);
    }

    //根据网段名segment查询对应网段下终端IP通信情况
    @Override
    public List<SegCommStatusVO> getSegCommStatus(String segment) {
        return netSegMappper.selectSegCommStatus("networksegment_"+segment.replace(".","_"));
    }

    //根据网段名查询在线终端数量、下行速率、上行速率随时间变化情况
    @Override
    public TerminalTrendVO getTerminalTrendStatus(String segment) {
        DecimalFormat df = new DecimalFormat("#.00");   //格式化double类型数据，保留两位小数
        List<TerminalTrend> networksegments = netSegMappper.getTerminalTrendStatus(segment);
        List<String> timeStamp = new ArrayList<>();
        List<String> uprate = new ArrayList<>();
        List<String> downrate = new ArrayList<>();
        List<Integer> onlineNum = new ArrayList<>();
        for (TerminalTrend ns : networksegments) {
            timeStamp.add(ns.getTimeStamp());
            uprate.add(df.format(ns.getUprate()/1024));
            downrate.add(df.format(ns.getDownrate()/1024));
            onlineNum.add(ns.getOndeviceCount());
        }
        return new TerminalTrendVO(timeStamp, uprate, downrate, onlineNum);

    }

    //根据网段名查询过去一小时的终端IP地区分布情况
    @Override
    public MapVO getlocation(String segment) {
        ArrayList<String> locs = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        String[] patterns = new String[]{".*内网.*","密歇根.*"};
        String segmentNet = "networksegment_" + segment.replace(".", "_");

        for (Location loc : netSegMappper.selectLocation(segmentNet)) {
            if (Pattern.matches(patterns[0], loc.getLocation())) {
                locs.add("广东");
                count.add(loc.getCount());
            } else {
                locs.add("其他");
                count.add(loc.getCount());
            }
        }
        return new MapVO(locs, count);
    }


}
