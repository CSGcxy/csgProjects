package com.cxy.checkservice.controller;


import com.cxy.checkservice.mapper.TestV1Mapper;
import com.cxy.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class TestV1Controller {

    @Autowired
    private TestV1Mapper mapper;

    /**
     * <p>测试是否能连接到数据库</p>
     */
    @ApiOperation(value = "测试是否能连接到数据库")
    @GetMapping("/test")
    public R getTest(){
        //以下是mybatis-plus写法，可以用mp也可以自己写xml
        return R.ok().data("test",mapper.selectList(null));
    }
}

