package com.cxy.checkoff.controller;

import com.cxy.checkoff.service.off.OffStatusService;
import com.cxy.commonutils.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "离线终端状态")
@RestController
@RequestMapping("/offTerminalStatus")
public class OffStatusController {

    @Autowired
    private OffStatusService offStatusService;

    /**
     * <p>
     * 查询offterminal表中的 不同时刻  的  离线终端数
     * </p>
     * @return 返回的是包含  不同时刻  的  离线终端数
     */
    @ApiOperation(value = "离线终端数")
    @GetMapping("/getOffTerminalCount")
    public R getOffTerminalCount() {
        return R.ok().data("offTerminalCountList",offStatusService.getOffterminalCount());
    }

    /**
     * <p>
     * 查询offterminal表中的  不同生产厂家  的  离线终端数
     * </p>
     * @return 返回的是包含不同网段的离线终端数
     */
    @ApiOperation(value = "不同生产厂家下的离线终端数")
    @GetMapping("/getOffTerminalManufactor")
    public R getOffTerminalManufactor() {
        return R.ok().data("offTerminalManufactorVO",offStatusService.getOffterminalManufactor());
    }


    /**
     * <p>
     * 查询offterminal表中的不同网段的离线终端数
     * </p>
     * @return 返回的是包含不同网段的离线终端数
     */
    @ApiOperation(value = "不同网段下离线终端数")
    @GetMapping("/getOffTerminalSegment")
    public R getOffTerminalSegment() {
        return R.ok().data("offTerminalSegmentList",offStatusService.getOffTerminalSegment());
    }

    /**
     * <p>
     * 查询offterminal表中的 不同地区  的  离线终端数
     * </p>
     * @return 返回的是包含  不同地区  的  离线终端数
     */
    @ApiOperation(value = "不同地区的离线终端数")
    @GetMapping("/getOffTerminalMap")
    public R getOffTerminalMap() {
        return R.ok().data("offTerminalMapList",offStatusService.getOffTerminalMap());
    }

    /**
     * <p>
     * 查询offterminal表中的 不同时刻下  不同网段(输入的参数)  的  离线终端数
     * </p>
     * @return 返回的是包含  不同时刻下  不同网段(输入的参数)  的  离线终端数
     */
    @ApiOperation(value = "不同时刻不同网段的离线终端数")
    @GetMapping("/getOffTerminalSegTimeSequence/{segment}")
    public R getOffTerminalSegTimeSequence(@PathVariable String segment) {
        return R.ok().data("offTerminalSegTimeSequenceList",offStatusService.getOffTerminalSegTimeSequence(segment));
    }
}
