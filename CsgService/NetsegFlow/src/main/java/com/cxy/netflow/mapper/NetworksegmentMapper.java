package com.cxy.netflow.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.netflow.entity.netseg.Location;
import com.cxy.netflow.entity.netseg.NetSegTotalBytes;
import com.cxy.netflow.entity.netseg.Networksegment;
import com.cxy.netflow.entity.netseg.TerminalTrend;
import com.cxy.netflow.entity.netseg.vo.AlertFlowVO;
import com.cxy.netflow.entity.netseg.vo.NetSegTotalVO;
import com.cxy.netflow.entity.netseg.vo.SegCommStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@DS(value = "sqlite1")
@Repository
@Mapper
public interface NetworksegmentMapper extends BaseMapper<Networksegment> {


    List<NetSegTotalVO> selectNetworkSegmentTerminalTotal(String segment);


    IPage<AlertFlowVO> getAlertFlow(Page<AlertFlowVO> page);

    IPage<SegCommStatusVO> selectSegCommStatus(String segment, Page<SegCommStatusVO> page);

    List<TerminalTrend> getTerminalTrendStatus(String segment);

    List<Location> selectLocation(String segment);

    List<NetSegTotalBytes> getSegTotalBytes(String segment);

    List<NetSegTotalBytes> getSegTotalBytesByTime(String segment, long startTime, long endTime);
}
