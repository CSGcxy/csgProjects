package com.cxy.offstatusservice.controller;
import com.cxy.commonutils.R;
import com.cxy.offstatusservice.entity.vo.*;
import com.cxy.offstatusservice.entity.vo.OffTerminalCountVO;
import com.cxy.offstatusservice.service.OffStatusService;
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
 *  离线终端状态
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
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
        OffTerminalCountVO offTerminalCountList = offStatusService.getOffterminalCount();
        return R.ok().data("offTerminalCountList",offTerminalCountList);
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
        OffTerminalManufactorVO offTerminalManufactorVO = offStatusService.getOffterminalManufactor();
        return R.ok().data("offTerminalManufactorVO",offTerminalManufactorVO);
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
        OffTerminalSegmentVO offTerminalSegmentList = offStatusService.getOffTerminalSegment();
        return R.ok().data("offTerminalSegmentList",offTerminalSegmentList);
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
        OffTerminalMapVO offTerminalMapList = offStatusService.getOffTerminalMap();
        return R.ok().data("offTerminalMapList",offTerminalMapList);
    }

    /**
     * <p>
     * 查询offterminal表中的 不同类别  的  离线终端数
     * </p>
     * @return 返回的是包含  不同类别  的  离线终端数
     */
//    @ApiOperation(value = "不同类别的离线终端数")
//    @GetMapping("/getOffTerminalCategory")
//    public R getOffTerminalCategory() {
//        OffTerminalCategoryVO offTerminalCategoryList = offStatusService.getOffTerminalCategory();
//        return R.ok().data("offTerminalCategoryList",offTerminalCategoryList);
//    }

    /**
     * <p>
     * 查询offterminal表中的 不同时刻下  不同网段(输入的参数)  的  离线终端数
     * </p>
     * @return 返回的是包含  不同时刻下  不同网段(输入的参数)  的  离线终端数
     */
    @ApiOperation(value = "不同时刻不同网段的离线终端数")
    @GetMapping("/getOffTerminalSegTimeSequence/{segment}")
    public R getOffTerminalSegTimeSequence(@PathVariable String segment) {
        OffTerminalCountVO offTerminalSegTimeSequenceList = offStatusService.getOffTerminalSegTimeSequence(segment);
        return R.ok().data("offTerminalSegTimeSequenceList",offTerminalSegTimeSequenceList);
    }

}

