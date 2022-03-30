package com.cxy.checkoff.service.check;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.checkoff.entity.check.TestV1;
import com.cxy.checkoff.entity.check.vo.AfnTimeVo;
import com.cxy.checkoff.entity.check.vo.PacketCountVo;
import com.cxy.checkoff.entity.check.vo.PacketDetailsVo;
import com.cxy.checkoff.entity.check.vo.PacketUnQualifiedCount;
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


    List<PacketCountVo> getPacketCount();

    PacketUnQualifiedCount getUnqualifiedPacketCount();

    AfnTimeVo getDifAfnCount(Integer second);

    PageInfo<PacketDetailsVo> getUnqualifiedDetails(Integer page, Integer pageSize);
//
//    AfnTimeVo getDifAfnCount(Integer second);
}
