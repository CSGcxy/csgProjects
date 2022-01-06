package com.cxy.netsegservice.service;

import com.cxy.netsegservice.entity.Networksegment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.netsegservice.entity.vo.*;

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

    List<AlertFlowVO> getAlertFlow();

    List<NetSegTotalVO> selectNetworkSegmentTerminalTotal(String segment);

    List<SegCommStatusVO> getSegCommStatus(String segment);

    TerminalTrendVO getTerminalTrendStatus(String segment);

    MapVO getlocation(String segment);

    NetSegTotalBytesVO getSegTotalBytes(String segment);

    NetSegTotalBytesVO getSegTotalBytesByTime(String segment, String time);

    List<String> getIpList(String segment);

}
