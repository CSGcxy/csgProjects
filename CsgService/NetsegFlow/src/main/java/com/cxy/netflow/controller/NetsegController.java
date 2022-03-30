package com.cxy.netflow.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.commonutils.R;
import com.cxy.netflow.entity.netseg.vo.NetSegTotalBytesVO;
import com.cxy.netflow.entity.netseg.vo.NetSegTotalVO;
import com.cxy.netflow.service.netseg.NetworksegmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "网段终端状态")
@RequestMapping("/terminalStatus")
@RestController
public class NetsegController {

    @Autowired
    private NetworksegmentService networksegmentService;

    /**
     * <p>根据传入的网段名，在networkSegment表中去查询总体统计表</p>
     * @param segment 传入的网段
     * @return 返回的是最新一分钟的数据
     */
    @ApiOperation(value = "根据网段名segment终端的整体情况")
    @GetMapping("/getNetworkSegmentTerminalTotal/{segment}")
    public R getNetworkSegmentTerminalTotal(@PathVariable String segment) {
        List<NetSegTotalVO> NetSegTotal = networksegmentService.selectNetworkSegmentTerminalTotal(segment);
        return R.ok().data("NetSegTotal", NetSegTotal);
    }

    /**
     * <p>查询出告警流数出现的时间、网段及对应的流数</p>
     * @return 只返回最近的前10条
     */
    @ApiOperation(value = "查询出告警流数出现的时间、IP及对应的流数")
    @GetMapping("/getAlertFlow")
    public R getAlertFlow() {

        return R.ok().data("AlertFlowList", networksegmentService.getAlertFlow(new Page<>(1,4)));
    }

    /**
     * <p>根据网段名segment查询终端通信情况</p>
     * @param segment 传入的网段
     * @return 返回对应网段下的不同终端IP对应的通信情况
     */
    @ApiOperation(value = "根据网段名segment查询终端通信情况")
    @GetMapping("/getSegCommStatus/{segment}/{current}")
    public R getSegCommStatus(@PathVariable String segment,
                              @PathVariable Integer current) {
        return R.ok().data("segCommStatusList", networksegmentService.getSegCommStatus(segment,new Page<>(current,5)));
    }

    /**
     * <p>根据网段名查询在线终端数量、下行速率、上行速率随时间变化情况</p>
     * @param segment 传入的网段
     * @return 返回的是过去一小时的数据量
     */
    @ApiOperation(value = "根据网段名查询在线终端数量、下行速率、上行速率随时间变化情况")
    @GetMapping("/getTerminalTreandStatus/{segment}")
    public R getTerminalTreandStatus(@PathVariable String segment) {
        return R.ok().data("terminalTrendList", networksegmentService.getTerminalTrendStatus(segment));
    }

    /**
     * <p>根据网段名查询过去一小时的终端IP地区分布情况</p>
     *
     * @param segment – 传入的网段
     * @return 返回的是对应的地理位置和数量
     */
    @ApiOperation(value = "根据网段名segment查询终端IP地区分布情况")
    @GetMapping("/getlocation/{segment}")
    public R getlocation(@PathVariable String segment) {
        return R.ok().data("locationList", networksegmentService.getlocation(segment));
    }

    /**
     * 根据输入的参数  网段  返回该网段下的流数(总字节数 = 发送字节数 + 接收字节数)
     * @param segment – 传入的网段
     * @return  返回时刻 和 该时刻的总字节数
     */
    @ApiOperation(value = "根据网段名segment查询网段下的流数情况")
    @GetMapping("/getSegTotalBytes/{segment}")
    public R getSegTotalBytes(@PathVariable String segment) {
        return R.ok().data("netSegTotalBytesVO", networksegmentService.getSegTotalBytes(segment));
    }

    /**
     *
     * @param segment 网段名
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 返回 时刻 和 该时刻字节总数
     */
    @ApiOperation(value = "根据网段名segment,开始时间startTime查询总流量随时间变化关系")
    @GetMapping("/getSegTotalBytesByTime/{segment}/{startTime}/{endTime}")
    public R getSegTotalBytesByTime(@PathVariable("segment") String segment,
                                    @PathVariable("startTime") long startTime,
                                    @PathVariable("endTime") long endTime) {
//        return R.ok().data("startTime", startTime).data("endTime", endTime);
        NetSegTotalBytesVO netSegTotalBytesVO = networksegmentService.getSegTotalBytesByTime(segment,startTime,endTime);
        return R.ok().data("netSegTotalBytesVO", netSegTotalBytesVO);
    }
}
