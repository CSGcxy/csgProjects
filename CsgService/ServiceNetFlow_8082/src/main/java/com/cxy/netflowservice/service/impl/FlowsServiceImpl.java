package com.cxy.netflowservice.service.impl;

import com.cxy.netflowservice.entity.FlowSankey;
import com.cxy.netflowservice.entity.Flows;
import com.cxy.netflowservice.entity.vo.ActiveFlowsVO;
import com.cxy.netflowservice.entity.vo.FlowSankeyVO;
import com.cxy.netflowservice.mapper.FlowsMapper;
import com.cxy.netflowservice.service.FlowsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.netflowservice.utils.TransferTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
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
        DecimalFormat df = new DecimalFormat("#.00");
        List<ActiveFlowsVO> activeFlowsVOList = flowsMapper.getActiveFlows(segment);
        for(ActiveFlowsVO activeFlowsVO : activeFlowsVOList) {
            String dur = activeFlowsVO.getDuration();
            activeFlowsVO.setFrontRate(Double.parseDouble(df.format(activeFlowsVO.getFrontRate()/1024)));
            activeFlowsVO.setBackRate(Double.parseDouble(df.format(activeFlowsVO.getBackRate()/1024)));
            activeFlowsVO.setByteCount(Double.parseDouble(df.format(activeFlowsVO.getByteCount()/1024)));
            activeFlowsVO.setDuration(TransferTime.converLongTimeToStr(Long.parseLong(activeFlowsVO.getDuration())/1000000));
        }
        return activeFlowsVOList;
    }


    @Override
    public FlowSankeyVO getFlowSankey(String segment) {
        List<FlowSankey> flowSankeyList = flowsMapper.getFlowSankey(segment);
        HashSet<String> ipList = new HashSet<>();
        for (FlowSankey fs : flowSankeyList) {
            ipList.add(fs.getSource());
            ipList.add(fs.getTarget());
        }
        return new FlowSankeyVO(flowSankeyList, ipList);
    }
}
