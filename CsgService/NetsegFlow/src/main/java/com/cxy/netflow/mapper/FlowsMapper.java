package com.cxy.netflow.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.netflow.entity.flows.FlowSankey;
import com.cxy.netflow.entity.flows.Flows;
import com.cxy.netflow.entity.flows.vo.ActiveFlowsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cxy
 * @since 2022-01-02
 */
@DS(value = "sqlite2")
@Repository
@Mapper
public interface FlowsMapper extends BaseMapper<Flows> {

    List<ActiveFlowsVO> getActiveFlows(String segment);

    List<FlowSankey> getFlowSankey(String segment);
}
