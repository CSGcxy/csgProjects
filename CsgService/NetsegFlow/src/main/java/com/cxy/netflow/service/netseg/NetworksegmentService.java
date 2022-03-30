package com.cxy.netflow.service.netseg;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.netflow.entity.netseg.Location;
import com.cxy.netflow.entity.netseg.Networksegment;
import com.cxy.netflow.entity.netseg.vo.*;

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


    List<NetSegTotalVO> selectNetworkSegmentTerminalTotal(String segment);

    IPage<AlertFlowVO> getAlertFlow(Page<AlertFlowVO> page);

    IPage<SegCommStatusVO> getSegCommStatus(String segment, Page<SegCommStatusVO> page);

    TerminalTrendVO getTerminalTrendStatus(String segment);

    List<Location> getlocation(String segment);

    NetSegTotalBytesVO getSegTotalBytes(String segment);

    NetSegTotalBytesVO getSegTotalBytesByTime(String segment, long startTime, long endTime);
}
