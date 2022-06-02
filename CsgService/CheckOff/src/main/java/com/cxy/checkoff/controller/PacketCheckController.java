package com.cxy.checkoff.controller;

import com.cxy.checkoff.entity.check.vo.PacketCountVo;
import com.cxy.checkoff.entity.check.vo.PacketUnQualifiedCount;
import com.cxy.checkoff.mapper.PacketCheckMapper;
import com.cxy.checkoff.service.check.PacketCheckService;
import com.cxy.commonutils.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "格式校验")
@RestController
@RequestMapping("/checkFormat")
public class PacketCheckController {

    @Autowired
    private PacketCheckService packetCheckService;

    @Autowired
    private PacketCheckMapper packetCheckMapper;

    /**
     * <p>查询最新2秒内不同afn包的数目</p>
     */
    @ApiOperation(value = "查询最新5秒内不同afn包的数目")
    @GetMapping("/getPacketCount")
    public R getPacketCount(){
        List<PacketCountVo> packetCountVoList = packetCheckService.getPacketCount();
        return R.ok().data("packetCountVoList",packetCountVoList);
    }

    /**
     * <p>查询最新时间2秒内合格包数目不合格包数目</p>
     */
    @ApiOperation(value = "查询最新时间5秒内合格包数目不合格包数目")
    @GetMapping("/getUnqualifiedPacketCount")
    public R getUnqualifiedPacketCount(){
        PacketUnQualifiedCount packetUnQualifiedCount = packetCheckService.getUnqualifiedPacketCount();
        return R.ok().data("packetUnQualifiedCount",packetUnQualifiedCount);
    }

    /**
     * <p>最近40秒（每隔5秒记录一次，入参5秒可调）统计不同afn的数目 /p>
     */
    @ApiOperation(value = "查询最新时间每隔{second}秒不同afn的合格包数目（最新时间内的几个时间区间）")
    @GetMapping("/getDifAfnCount/{second}")
    public R getDifAfnCount(@PathVariable("second") Integer second){
        return R.ok().data("afnVoList",packetCheckService.getDifAfnCount(second));
    }

    /**
     * <p>分页查询5s内不合规packet记录明细</p>
     */
    @ApiOperation(value = "分页查询5s内不合规packet记录明细")
    @GetMapping("/getUnqualifiedDetails/{page}/{pageSize}")
    public R getUnqualifiedDetails(@PathVariable("page") Integer page,
                                   @PathVariable("pageSize") Integer pageSize) {
        return R.ok().data("unqualifiedDetails", packetCheckService.getUnqualifiedDetails(page,pageSize));
    }

    /**
     * <p>查询合格包比例</p>
     */
    @ApiOperation(value = "查询合格包比例")
    @GetMapping("/getPackageScore")
    public R getPackageScore(){
        Integer totalCount = packetCheckMapper.getTotalCount();
        Integer normalCount = packetCheckMapper.getNormalCount();
        Integer packageScore = 100 * normalCount / totalCount;

        return R.ok().data("packageScore",packageScore);
    }
}
