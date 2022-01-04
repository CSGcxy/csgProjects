package com.cxy.netflowservice.service.impl;

import com.cxy.netflowservice.entity.FlowSankey;
import com.cxy.netflowservice.entity.Flows;
import com.cxy.netflowservice.entity.vo.ActiveFlowsVO;
import com.cxy.netflowservice.entity.vo.FlowSankeyVO;
import com.cxy.netflowservice.mapper.FlowsMapper;
import com.cxy.netflowservice.service.FlowsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxy
 * @since 2022-01-02
 */
@Service
public class FlowsServiceImpl extends ServiceImpl<FlowsMapper, Flows> implements FlowsService {
    @Autowired
    private FlowsMapper flowsMapper;

    @Override
    public List<ActiveFlowsVO> getActiveFlows(String segment) {
        return flowsMapper.getActiveFlows(segment);
    }

    @Override
    public FlowSankeyVO getFlowSankey() {
        List<FlowSankey> flowSankeyList = flowsMapper.getFlowSankey();
        System.out.println(flowSankeyList);
        List<String> ipList = new ArrayList<>();
        for(FlowSankey fs : flowSankeyList) {
            ipList.add(fs.getSRC_IP());
            ipList.add(fs.getDST_IP());
        }
        return new FlowSankeyVO(flowSankeyList, ipList);
    }
}
