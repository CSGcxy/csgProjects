package com.cxy.netflow.service.flows;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.netflow.entity.flows.Flows;
import com.cxy.netflow.entity.flows.vo.ActiveFlowsVO;
import com.cxy.netflow.entity.flows.vo.FlowSankeyVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxy
 * @since 2022-01-02
 */
public interface FlowsService extends IService<Flows> {

    PageInfo<ActiveFlowsVO> getActiveFlows(String segment, Integer current);

    FlowSankeyVO getFlowSankey(String segment);
}
