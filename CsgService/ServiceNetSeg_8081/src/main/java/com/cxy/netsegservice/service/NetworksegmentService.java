package com.cxy.netsegservice.service;

import com.cxy.netsegservice.entity.Location;
import com.cxy.netsegservice.entity.Networksegment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.netsegservice.entity.vo.*;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
public interface NetworksegmentService extends IService<Networksegment> {

    NetSegVO selectNetSeg();

    PageInfo<AlertFlowVO> getAlertFlow();

    List<NetSegTotalVO> selectNetworkSegmentTerminalTotal(String segment);

    PageInfo<SegCommStatusVO> getSegCommStatus(String segment);

    TerminalTrendVO getTerminalTrendStatus(String segment);

    List<Location> getlocation(String segment);

    NetSegTotalBytesVO getSegTotalBytes(String segment);

    NetSegTotalBytesVO getSegTotalBytesByTime(String segment, long startTime,long endTime);

    List<String> getIpList(String segment);

}
