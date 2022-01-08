package com.cxy.netsegservice.mapper;

import com.cxy.netsegservice.entity.Location;
import com.cxy.netsegservice.entity.NetSegTotalBytes;
import com.cxy.netsegservice.entity.Networksegment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.netsegservice.entity.TerminalTrend;
import com.cxy.netsegservice.entity.vo.AlertFlowVO;
import com.cxy.netsegservice.entity.vo.NetSegTotalVO;
import com.cxy.netsegservice.entity.vo.SegCommStatusVO;
import org.apache.ibatis.annotations.Param;
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
@Repository
public interface NetworksegmentMapper extends BaseMapper<Networksegment> {

    List<String> selectNetSeg();

    List<NetSegTotalVO> selectNetworkSegmentTerminalTotal(String segment);

    List<SegCommStatusVO> selectSegCommStatus(@Param("segment") String segment);

    List<TerminalTrend> getTerminalTrendStatus(String segment);

    List<Location> selectLocation(@Param("segment") String segment);

    List<NetSegTotalBytes> getSegTotalBytes(@Param("segment") String segment);

    List<NetSegTotalBytes> getSegTotalBytesByTime(String segment, long startTime, long endTime);

    List<AlertFlowVO> getAlertFlow();

    List<String> selectIpList(@Param("segment") String segment);
}
