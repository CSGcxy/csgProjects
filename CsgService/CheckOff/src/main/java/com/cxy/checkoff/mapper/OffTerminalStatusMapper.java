package com.cxy.checkoff.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.checkoff.entity.off.*;
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
@DS("sqlite2")
@Repository
@Mapper
public interface OffTerminalStatusMapper extends BaseMapper<OffTerminalStatus> {


    List<OffTerminalCount> getOffterminalCount();

    List<OffTerminalManufactor> getOffterminalManufactor();

    List<OffTerminalSegment> getOffterminalSegment();

    List<OffTerminalMap> getOffterminalMap();

    List<OffTerminalCategory> getOffterminalCategory();

    List<OffTerminalCount> getOffTerminalSegTimeSequence(String segment);
}
