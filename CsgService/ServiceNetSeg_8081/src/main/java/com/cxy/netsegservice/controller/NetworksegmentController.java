package com.cxy.netsegservice.controller;


import com.cxy.commonutils.R;
import com.cxy.netsegservice.entity.vo.*;
import com.cxy.netsegservice.service.NetworksegmentService;
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
 *  网段终端状态
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@Api(tags = "网段终端状态")
@RestController
@RequestMapping("/terminalStatus")
public class NetworksegmentController {
    @Autowired
    private NetworksegmentService networksegmentService;

    /**
     * <p>
     * 查询networkSegment表中的网段名
     * </p>
     * @return 返回的是包含网段名list
     */
    @ApiOperation(value = "网段终端的整体情况")
    @GetMapping("/getNetSegs")
    public R getNetSegs() {
        NetSegVO netSegList = networksegmentService.selectNetSeg();
        /**
         * 下面注释部分使用mybatis-plus的查询器，查询最新时间对应的网段情况，但不方便
         */
//        QueryWrapper<Networksegment> netSegWrapper = new QueryWrapper<>();
//        netSegWrapper.orderByDesc("TIMESTAMP");
//        netSegWrapper.last("limit 1");
//        List<Networksegment> list = networksegmentService.list(netSegWrapper);
        return R.ok().data("NetSegs",netSegList);
    }

    /**
     * <p>根据传入的网段名，在networkSegment表中去查询总体统计表</p>
     * @param segment 传入的网段
     * @return 返回的是最新一分钟的数据
     */
    @GetMapping("/getNetworkSegmentTerminalTotal/{segment}")
    public R getNetworkSegmentTerminalTotal(@PathVariable String segment) {
        List<NetSegTotalVO> NetSegTotal = networksegmentService.selectNetworkSegmentTerminalTotal(segment);
        return R.ok().data("NetSegTotal", NetSegTotal);
    }

    /**
     * <p>查询出告警流数出现的时间、IP及对应的流数</p>
     * @return 只返回最近的前10条
     */
    @ApiOperation(value = "查询出告警流数出现的时间、IP及对应的流数")
    @GetMapping("/getAlertFlow")
    public R getAlertFlow() {
        List<AlertFlowVO> AlertFlowList = networksegmentService.getAlertFlow();
        return R.ok().data("AlertFlowList", AlertFlowList);
    }

    /**
     * <p>根据网段名segment查询终端通信情况</p>
     * @param segment 传入的网段
     * @return 返回对应网段下的不同终端IP对应的通信情况
     */
    @ApiOperation(value = "根据网段名segment查询终端通信情况")
    @GetMapping("/getSegCommStatus/{segment}")
    public R getSegCommStatus(@PathVariable String segment) {
        List<SegCommStatusVO> segCommStatusVOList = networksegmentService.getSegCommStatus(segment);
        return R.ok().data("segCommStatusList", segCommStatusVOList);
    }

    /**
     * <p>根据网段名查询在线终端数量、下行速率、上行速率随时间变化情况</p>
     * @param segment 传入的网段
     * @return 返回的是过去一小时的数据量
     */
    @ApiOperation(value = "根据网段名查询在线终端数量、下行速率、上行速率随时间变化情况")
    @GetMapping("/getTerminalTreandStatus/{segment}")
    public R getTerminalTreandStatus(@PathVariable String segment) {
        TerminalTrendVO terminalTrendList = networksegmentService.getTerminalTrendStatus(segment);
        return R.ok().data("terminalTrendList", terminalTrendList);
    }

    /**
     * <p>根据网段名查询过去一小时的终端IP地区分布情况</p>
     *
     * @param segment – 传入的网段
     * @return 返回的是对应的地理位置和数量
     */
    @GetMapping("/getlocation/{segment}")
    public R getlocation(@PathVariable String segment) {
        MapVO mapList = networksegmentService.getlocation(segment);
        return R.ok().data("mapList", mapList);
    }

}

