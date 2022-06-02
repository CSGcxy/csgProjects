package com.cxy.assessservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.assessservice.api.CheckClientApi;
import com.cxy.assessservice.entity.vo.SegScoreAllTimeVo;
import com.cxy.assessservice.entity.vo.ratioEntity.PageInfoVo;
import com.cxy.assessservice.mapper.OffTerminalMapper;
import com.cxy.assessservice.service.NetworksegmentService;
import com.cxy.commonutils.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;


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

    @Autowired
    private OffTerminalMapper offTerminalMapper;

    @Resource
    private CheckClientApi checkClientApi;

    @ApiOperation(value = "测试服务的调用")
    @GetMapping("/testCheck")
    public R testCheck() {
//        return checkClientApi.getUnqualifiedPacketCount();
        return checkClientApi.getUnqualifiedDetails(1, 10);
    }

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
    public R getAssessScore() throws ParseException {

        SegScoreAllTimeVo segScoreAllTimeVo = networksegmentService.getAllSegScoreDetails();  // 返回各网段的6个指标评分及总分

        return R.ok().data("segScoreAllTimeVo",segScoreAllTimeVo);
    }

    /**
     * <p>根据入参  网段名  查询该网段下总速率评分倒数10名  的终端信息   包括 时间 ip 位置 上行速率 下行速率 总速率 总速率的评分(依据上行速率、下行速率和总速率的评分结合熵值法确定)</p>
     */
    @ApiOperation(value = "查询安全评估页面 输入网段 下的速率综合评分倒数前10的终端信息")
    @GetMapping("/getTerminalTotalRateScore/{pageNum}/{pageSize}")
    public R getTerminalTotalRateScore(@PathVariable Integer pageNum,
                                       @PathVariable Integer pageSize) throws ParseException {

        SegScoreAllTimeVo  segScoreEntityVoList = networksegmentService.getAllSegScoreDetails();  // 返回各网段的6个指标评分及总分
        PageInfoVo pageInfoVo = networksegmentService.getTerminalScoreDetails(segScoreEntityVoList,pageNum,pageSize);  // 返回各网段的6个指标评分及总分

        return R.ok().data("pageInfoVo",pageInfoVo);
    }

    /**
     *
     */
    @ApiOperation(value = "查询安全评估页面 IPTerminal表中在线终端比例")
    @GetMapping("/getOnlineIPScore")
    public R getOnlineAndTypeScore(){
        Integer totalCount = offTerminalMapper.getTotalCount();   // 返回最新100条数据的终端数(查询条件 Dno-100 出现了97条结果,所以查Dno-100不一定有100个终端)
        Integer onlineCountIP = offTerminalMapper.getOnlineIPCount();  // 返回最新100条数据的IP终端在线终端数
        Integer onlineIPScore = 100 * onlineCountIP / totalCount;

        return R.ok().data("onlineIPScore",onlineIPScore);
    }

    @ApiOperation(value = "查询安全评估页面 NIPTerminal表中在线终端比例")
    @GetMapping("/getOnlineNIPScore")
    public R getOnlineNIPScore(){
        Integer totalCount = offTerminalMapper.getTotalCount();   // 返回最新100条数据的终端数(查询条件 Dno-100 出现了97条结果,所以查Dno-100不一定有100个终端)
        Integer onlineCountNIP = offTerminalMapper.getOnlineNIPCount();  // 返回最新100条数据的非IP终端在线终端数

        Integer onlineNIPScore = 100 * onlineCountNIP / totalCount;
        return R.ok().data("onlineNIPScore",onlineNIPScore);
    }

    @ApiOperation(value = "查询安全评估页面 Terminal表中已知设备终端比例")
    @GetMapping("/getExplicitScore")
    public R getExplicitScore(){
        Integer totalCount = offTerminalMapper.getTotalCount();   // 返回最新100条数据的终端数(查询条件 Dno-100 出现了97条结果,所以查Dno-100不一定有100个终端)
        Integer ExplicitCount = offTerminalMapper.getExplicitCount();  // 返回最新100条数据的非IP终端在线终端数

        Integer explicitScore = 100 * ExplicitCount / totalCount;
        return R.ok().data("explicitScore",explicitScore);
    }

    @ApiOperation(value = "查询正常包比例")
    @GetMapping("/getPackageScore")
    public R getPackageScore() {
//        return checkClientApi.getUnqualifiedPacketCount();
        return checkClientApi.getPackageScore();
    }
}

