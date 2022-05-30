package com.cxy.assessservice.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.assessservice.entity.Networksegment;
import com.cxy.assessservice.entity.vo.ratioEntity.*;
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
@DS(value = "sqlite2")
@Repository
@Mapper
public interface OffTerminalMapper extends BaseMapper<Networksegment> {

    // 返回最新100条数据的终端数(查询条件 Dno-100 出现了97条结果,所以查Dno-100不一定有100个终端)
    Integer getTotalCount();
    // 返回最新100条数据的在线终端数
    Integer getOnlineCount();
    // 返回最新100条数据的已知类型的终端数
    Integer getExplicitCount();
}
