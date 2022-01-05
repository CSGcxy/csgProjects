package com.cxy.netflowservice.controller;


import com.cxy.commonutils.R;
import com.cxy.netflowservice.entity.vo.ActiveFlowsVO;
import com.cxy.netflowservice.entity.vo.FlowSankeyVO;
import com.cxy.netflowservice.service.FlowsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cxy
 * @since 2022-01-02
 */
@Api(tags = "网段流量")
@RestController
@RequestMapping("/flows")
public class FlowsController {
    @Autowired
    private FlowsService flowsService;

    @ApiOperation(value = "查询活跃流")
    @GetMapping("/getActiveFlows/{segment}")
    public R getActiveFlows(@PathVariable("segment") String segment) {
        System.out.println(segment);
        List<ActiveFlowsVO> activeFlowsVOList = flowsService.getActiveFlows(segment);
        return R.ok().data("activeFlowsList", activeFlowsVOList);
    }

    @ApiOperation(value = "查询网段整体桑基图")
    @GetMapping("/getFlowSankey/{segment}")
    public R getFlowSankey(@PathVariable("segment") String segment) {
        FlowSankeyVO flowSankeyVOList = flowsService.getFlowSankey(segment);
        return R.ok().data("flowSankeyVOList",flowSankeyVOList);
    }

}

