package com.cxy.netsegservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.baseService.handler.CsgException;
import com.cxy.netsegservice.Utils.TimeToStamp;
import com.cxy.netsegservice.entity.Location;
import com.cxy.netsegservice.entity.NetSegTotalBytes;
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
import java.text.ParseException;
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
        // 定义时间格式
        String format = "yyyy/MM/dd HH:mm:ss";
        // mybatis plus 条件构造器queryWrapper
//        QueryWrapper<Networksegment> netSegWrapper = new QueryWrapper<>();
//        netSegWrapper.ge("Alert_flow", 0);  // 告警流数Alert_flow > 0
//        netSegWrapper.orderByDesc("TIMESTAMP"); // 以时间戳TIMESTAMP排序
//        netSegWrapper.last("limit 10");          // 限制记录条数在10条以内
//        List<Networksegment> networksegments = baseMapper.selectList(netSegWrapper); // 获取满足以上条件的10个网段 即网段内有告警流数 以时间顺序排序

        List<Networksegment> networksegments = netSegMappper.getAlertFlow();
        // 没有任何网段存在告警流,则报错"无告警流"
        if (networksegments == null) {
            throw new CsgException(20001, "无告警流");
        }
        // 并非将告警流所在网段的所有信息都返回,而是构造一个新的返回体AlertFlowVO 选择性截取网段中的字段返回
        List<AlertFlowVO> alertFlowVOList = new ArrayList<>();
        // 规范时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 对获得的告警流所在的10个网段遍历 将 网段 网段时间戳 网段的告警流数 三个字段逐一取出来放置到响应体List<AlertFlowVO>中
        for (Networksegment netSeg : networksegments) {
            AlertFlowVO alertFlowVO = new AlertFlowVO();
            //因为数据库存储的时间戳不是以Date类型存储的，而是Integer类型，所以这里需要将其格式化
            alertFlowVO.setTimestamp(sdf.format(netSeg.getTimestamp())); // 网段时间戳
            alertFlowVO.setNetworkseg(netSeg.getNetworkseg());           // 网段
            alertFlowVO.setAlertFlow(netSeg.getAlertFlow());             // 网段的告警流数
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
//        return netSegMappper.selectSegCommStatus("networksegment_"+segment.replace(".","_"));
        return netSegMappper.selectSegCommStatus(segment);
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
//        String[] patterns = new String[]{".*内网.*","密歇根.*"};
//        String segmentNet = "networksegment_" + segment.replace(".", "_");

        for (Location loc : netSegMappper.selectLocation(segment)) {
            locs.add(loc.getLocation());
            count.add(loc.getCount());
//            if (Pattern.matches(patterns[0], loc.getLocation())) {
//                locs.add("广东");
//                count.add(loc.getCount());
//            } else {
//                locs.add("其他");
//                count.add(loc.getCount());
//            }
        }
        return new MapVO(locs, count);
    }

    //根据网段名查询过去一小时的终端IP地区分布情况
    @Override
    public NetSegTotalBytesVO getSegTotalBytes(String segment) {

        List<NetSegTotalBytes> networksegments = netSegMappper.getSegTotalBytes(segment);
        List<String> timestampList = new ArrayList<>();
        List<Integer> totalBytesList = new ArrayList<>();

        for (NetSegTotalBytes ns : networksegments) {
            timestampList.add(ns.getTimestamp());
            totalBytesList.add(ns.getTotalbytes());
        }
//        for (Networksegment networksegment : netSegMappper.getSegTotalBytes(segment)) {
//            timestampList.add(networksegment.getTimestamp());
//            totalBytesList.add(networksegment.getTotalbytes());
//        }
        return new NetSegTotalBytesVO(timestampList, totalBytesList);
    }

    @Override
    public NetSegTotalBytesVO getSegTotalBytesByTime(String segment, String time) {
        long startTime = 0;
        try {
            startTime = TimeToStamp.dateToStamp(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<NetSegTotalBytes> networksegments = netSegMappper.getSegTotalBytesByTime(segment,startTime);
        List<String> timestampList = new ArrayList<>();
        List<Integer> totalBytesList = new ArrayList<>();

        for (NetSegTotalBytes ns : networksegments) {
            timestampList.add(ns.getTimestamp());
            totalBytesList.add(ns.getTotalbytes());
        }
        return new NetSegTotalBytesVO(timestampList, totalBytesList);
    }

    @Override
    public List<String> getIpList(String segment) {
        return netSegMappper.selectIpList(segment);
    }


}
