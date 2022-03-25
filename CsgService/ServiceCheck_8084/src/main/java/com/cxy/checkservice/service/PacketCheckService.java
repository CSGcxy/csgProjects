package com.cxy.checkservice.service;

import com.cxy.checkservice.entity.TestV1;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.checkservice.entity.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxy
 * @since 2022-03-16
 */
public interface PacketCheckService extends IService<TestV1> {


    // 查询不同afn包的数目
    List<PacketCountVo> getPacketCount();

    // 查询合格包和不合格包数目
    PacketUnQualifiedCount getUnqualifiedPacketCount();

    // 每隔2秒查询不同的合格包数目
    AfnTimeVo getDiffrentAfnCount(Integer second);

    // 分页查询5s内不合规packet记录明细
    PageInfo<PacketDetailsVo> getUnqualifiedDetails(Integer page,Integer pageSize);

    AfnTimeVo getDifAfnCount(Integer second);
}
