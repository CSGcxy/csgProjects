package com.cxy.checkservice.mapper;

import com.cxy.checkservice.entity.TestV1;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.checkservice.entity.vo.Afn;
import com.cxy.checkservice.entity.vo.AfnVo;
import com.cxy.checkservice.entity.vo.PacketCountVo;
import com.cxy.checkservice.entity.vo.PacketDetailsVo;
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
@Repository
public interface PacketCheckMapper extends BaseMapper<TestV1> {

    // 查询最新时间2秒内不同afn包的数目
    List<PacketCountVo> getPacketCount();

    // 查询最新时间2秒内不合格包数目
    Integer getUnqualifiedPacketCount();

    // 查询最新时间2秒内合格包数目
    Integer getQualifiedPacketCount();

    // 距离最新时间每隔2秒查询不同的合格包数目
    List<Afn> getDiffrentAfnCount(Integer startTime,Integer endTime);

    // 查询距离当前最新时间 second 秒 是什么时间
    String getTime(Integer second);

    // 分页查询最近5秒内不合格packet详情
    List<PacketDetailsVo> getUnqualifiedDetails();

    // 查出数据库中所有afn
    List<Integer> getAfnList(Integer second);

    // 查出时间列表放入响应体
    List<String> getTimeList(Integer second);

    // 查出afn = anfSingle在上述时间的值列表
    List<Integer> getAnfCountList(Integer afnSingle,Integer second);

    // 单独根据时间和afn查afn的数目
    int getSpecialCountList(String timeSingle, Integer afnSingle,Integer second);

    int getAfnTotalCount(Integer afnSingle, Integer second);
}
