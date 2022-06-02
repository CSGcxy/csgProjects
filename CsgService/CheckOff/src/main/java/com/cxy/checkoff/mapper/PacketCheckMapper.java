package com.cxy.checkoff.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.checkoff.entity.check.TestV1;
import com.cxy.checkoff.entity.check.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cxy
 * @since 2022-03-16
 */
@DS("sqlite1")
@Repository
@Mapper
public interface PacketCheckMapper extends BaseMapper<TestV1> {

    // 查询最新时间5秒内不同afn包的数目
    List<PacketCountVo> getPacketCount();

    // 查询最新时间5秒内合格包和不合格包数目
    PacketUnQualifiedCount getQualifiedCount();

    // 距离最新时间每隔5秒查询不同的合格包数目
    List<AfnPeriod> getDifAfnCount(Integer second);

    //查询最新20s内出现的合规afn种类
    List<Integer> selectDifAfns();

    // 分页查询最近5秒内不合格packet详情
    List<PacketDetailsVo> getUnqualifiedDetails();

    // 查询20s内总包数(用于求正常包分数)
    Integer getTotalCount();

    // 查询20s内正常包数(用于求正常包分数)
    Integer getNormalCount();

}
