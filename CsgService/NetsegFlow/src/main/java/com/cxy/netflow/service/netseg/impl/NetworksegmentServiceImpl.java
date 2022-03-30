package com.cxy.netflow.service.netseg.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cxy.netflow.entity.netseg.Location;
import com.cxy.netflow.entity.netseg.NetSegTotalBytes;
import com.cxy.netflow.entity.netseg.Networksegment;
import com.cxy.netflow.entity.netseg.TerminalTrend;
import com.cxy.netflow.entity.netseg.vo.*;
import com.cxy.netflow.mapper.NetworksegmentMapper;
import com.cxy.netflow.service.netseg.NetworksegmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
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


    //根据传入的网段名，在networkSegment表中去查询总体统计表
    @Override
    public List<NetSegTotalVO> selectNetworkSegmentTerminalTotal(String segment) {
        return netSegMappper.selectNetworkSegmentTerminalTotal(segment);
    }

    //查询出告警流数出现的时间、网段及对应的流数
    @Override
    public IPage<AlertFlowVO> getAlertFlow(Page<AlertFlowVO> page) {
        return netSegMappper.getAlertFlow(page);
    }

    //根据网段名segment查询对应网段下终端IP通信情况
    @Override
    public IPage<SegCommStatusVO> getSegCommStatus(String segment, Page<SegCommStatusVO> page) {
        return netSegMappper.selectSegCommStatus(segment, page);
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
    public List<Location> getlocation(String segment) {
        return netSegMappper.selectLocation(segment);
    }

    //根据网段名segment查询网段下的流数情况
    @Override
    public NetSegTotalBytesVO getSegTotalBytes(String segment) {
        List<NetSegTotalBytes> networksegments = netSegMappper.getSegTotalBytes(segment);
        List<String> timestampList = new ArrayList<>();
        List<Integer> totalBytesList = new ArrayList<>();

        for (NetSegTotalBytes ns : networksegments) {
            timestampList.add(ns.getTimestamp());
            totalBytesList.add(ns.getTotalbytes());
        }
        return new NetSegTotalBytesVO(timestampList, totalBytesList);
    }

    @Override
    public NetSegTotalBytesVO getSegTotalBytesByTime(String segment, long startTime, long endTime) {
        List<NetSegTotalBytes> networksegments = netSegMappper.getSegTotalBytesByTime(segment, startTime, endTime);
        List<String> timestampList = new ArrayList<>();
        List<Integer> totalBytesList = new ArrayList<>();

        for (NetSegTotalBytes ns : networksegments) {
            timestampList.add(ns.getTimestamp());
            totalBytesList.add(ns.getTotalbytes());
        }
        return new NetSegTotalBytesVO(timestampList, totalBytesList);
    }


}
