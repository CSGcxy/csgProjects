package com.cxy.offstatusservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.offstatusservice.entity.*;
import com.cxy.offstatusservice.entity.vo.OffTerminalCountVO;
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
public interface OffTerminalStatusMapper extends BaseMapper<OffTerminalStatus> {


    List<OffTerminalCount> getOffterminalCount();

    List<OffTerminalManufactor> getOffterminalManufactor();

    List<OffTerminalSegment> getOffterminalSegment();

    List<OffTerminalMap> getOffterminalMap();

    List<OffTerminalCategory> getOffterminalCategory();

    List<OffTerminalCount> getOffTerminalSegTimeSequence(String segment);
}
