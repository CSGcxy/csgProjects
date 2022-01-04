package com.cxy.netflowservice.mapper;

import com.cxy.netflowservice.entity.Flows;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.netflowservice.entity.vo.ActiveFlowsVO;
import com.cxy.netflowservice.entity.FlowSankey;
import org.apache.ibatis.annotations.Param;
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
@Repository
public interface FlowsMapper extends BaseMapper<Flows> {

    List<ActiveFlowsVO> getActiveFlows(@Param("segment") String segment);

    List<FlowSankey> getFlowSankey();
}
