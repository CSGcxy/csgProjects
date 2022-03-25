package com.cxy.checkservice.controller;


import com.cxy.checkservice.entity.vo.AfnTimeVo;
import com.cxy.checkservice.entity.vo.AfnVo;
import com.cxy.checkservice.entity.vo.PacketCountVo;
import com.cxy.checkservice.entity.vo.PacketUnQualifiedCount;
import com.cxy.checkservice.mapper.PacketCheckMapper;
import com.cxy.checkservice.service.PacketCheckService;
import com.cxy.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cxy
 * @since 2022-03-16
 */
@Api(tags = "格式校验")
@RestController
public class PacketCheckController {

    @Autowired
    private PacketCheckMapper packetCheckMapper;

    @Autowired
    private PacketCheckService packetCheckService;

    /**
     * <p>测试是否能连接到数据库</p>
     */
    @ApiOperation(value = "测试是否能连接到数据库")
    @GetMapping("/test")
    public R getTest(){
        //以下是mybatis-plus写法，可以用mp也可以自己写xml
        return R.ok().data("test", packetCheckMapper.selectList(null));
    }

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
     * <p>最近8秒（每隔2秒记录一次，入参2秒可调）不同afn的数目的 返回4个对象，分别是8秒前-6秒前区间 6秒前-4秒前区间 ..。不同afn数目/p>
     */
    @GetMapping("/getDiffrentAfnCount/{second}")
    public R getDiffrentAfnCount(@PathVariable("second") Integer second){
        AfnTimeVo afnVoList = packetCheckService.getDiffrentAfnCount(second);
        return R.ok().data("afnVoList",afnVoList);
    }
    /**
     * <p>对上面 getDiffrentAfnCount方法进行了改进，只需要查一次数据库，然后对数据进行筛选</p>
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


}

