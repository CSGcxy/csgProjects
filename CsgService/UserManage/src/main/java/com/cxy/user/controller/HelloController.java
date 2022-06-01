package com.cxy.user.controller;

import com.cxy.commonutils.common.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bing_  @create 2022/1/4-21:41
 */
@Api(tags = "测试接口")
@RestController
public class HelloController {

    /**
     * http://localhost:8888/hello
     * ("hasAuthority('test')") // 具有 test 权限才能访问此接口
     */
    @ApiOperation(value = "查询测试1")
    @GetMapping("/test/hello")
    //@PreAuthorize("hasAuthority('system:dept:list')")
//    @PreAuthorize("@ex.hasAuthority('system:dept:list')")
    public String hello() {
        return "hello";
    }


    @ApiOperation(value = "测试查询2")
    @GetMapping("/testCors")
    public ResponseResult testCors() {
        return new ResponseResult(200, "testCors");
    }
}
