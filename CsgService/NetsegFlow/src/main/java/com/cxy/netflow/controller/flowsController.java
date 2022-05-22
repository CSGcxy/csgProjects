package com.cxy.netflow.controller;

import com.cxy.commonutils.common.R;
import com.cxy.netflow.entity.flows.vo.FlowSankeyVO;
import com.cxy.netflow.service.flows.FlowsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "网段流量")
@RequestMapping("/flows")
@RestController
public class flowsController {

    @Autowired
    private FlowsService flowsService;

    @ApiOperation(value = "查询活跃流")
    @GetMapping("/getActiveFlows/{segment}/{current}")
    public R getActiveFlows(@PathVariable("segment") String segment,
                            @PathVariable Integer current) {
        return R.ok().data("activeFlowsList", flowsService.getActiveFlows(segment,current));
    }

    @ApiOperation(value = "查询网段整体桑基图")
    @GetMapping("/getFlowSankey/{segment}")
    public R getFlowSankey(@PathVariable("segment") String segment) {
        FlowSankeyVO flowSankeyVOList = flowsService.getFlowSankey(segment);
        return R.ok().data("flowSankeyVOList",flowSankeyVOList);
    }

}
