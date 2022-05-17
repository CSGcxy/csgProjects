package com.cxy.assessservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.assessservice.entity.vo.SegScoreEntityVo;
import com.cxy.assessservice.entity.vo.TerminalScoreEntityVo;
import com.cxy.assessservice.service.NetworksegmentService;
import com.cxy.assessservice.service.SafetyAssessService;
import com.cxy.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  网段终端状态
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@Api(tags = "安全评估页面")
@RestController
public class NetworksegmentController {
    @Autowired
    NetworksegmentService networksegmentService;

    /**
     * <p>测试是否能连接到数据库</p>
     */
    @ApiOperation(value = "测试是否能连接到数据库")
    @GetMapping("/test")
    public R getTest(){

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("ONDEVICECOUNT",1004);
        //以下是mybatis-plus写法，可以用mp也可以自己写xml
        return R.ok().data("test", networksegmentService.getOne(wrapper));
    }


    /**
     * <p>查询网段的  上行速率、下行速率、在线终端、离线终端、告警流数、活跃流数  6个指标的打分及熵值法确定的总分</p>
     */
    @ApiOperation(value = "查询安全评估页面 不同网段的6个指标打分及总分")
    @GetMapping("/getSegAssessScore")
    public R getAssessScore() {

        List<SegScoreEntityVo> segScoreEntityVoList = networksegmentService.getAllSegScoreDetails();  // 返回各网段的6个指标评分及总分

        return R.ok().data("segScoreEntityVoList",segScoreEntityVoList);
    }

    /**
     * <p>根据入参  网段名  查询该网段下总速率评分倒数10名  的终端信息   包括 时间 ip 位置 上行速率 下行速率 总速率 总速率的评分(依据上行速率、下行速率和总速率的评分结合熵值法确定)</p>
     */
    @ApiOperation(value = "查询安全评估页面 输入网段 下的速率综合评分倒数前10的终端信息")
    @GetMapping("/getTerminalTotalRateScore")
    public R getTerminalTotalRateScore() {

        List<SegScoreEntityVo> segScoreEntityVoList = networksegmentService.getAllSegScoreDetails();  // 返回各网段的6个指标评分及总分
        List<TerminalScoreEntityVo> terminalScoreEntityVoList = networksegmentService.getTerminalScoreDetails(segScoreEntityVoList);  // 返回各网段的6个指标评分及总分

        return R.ok().data("terminalScoreEntityVoList",terminalScoreEntityVoList);
    }

}

