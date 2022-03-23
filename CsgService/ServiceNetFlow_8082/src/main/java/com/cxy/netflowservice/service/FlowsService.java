package com.cxy.netflowservice.service;

import com.cxy.netflowservice.entity.Flows;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.netflowservice.entity.vo.ActiveFlowsVO;
import com.cxy.netflowservice.entity.vo.FlowSankeyVO;
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
