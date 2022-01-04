package com.cxy.netflowservice.service;

import com.cxy.netflowservice.entity.Flows;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.netflowservice.entity.vo.ActiveFlowsVO;
import com.cxy.netflowservice.entity.vo.FlowSankeyVO;

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

    List<ActiveFlowsVO> getActiveFlows(String segment);

    FlowSankeyVO getFlowSankey();
}
