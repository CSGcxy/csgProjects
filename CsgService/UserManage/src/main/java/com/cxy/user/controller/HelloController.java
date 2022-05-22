package com.cxy.user.controller;

import com.cxy.commonutils.common.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bing_  @create 2022/1/4-21:41
 */
@RestController
public class HelloController {

    /**
     * http://localhost:8888/hello
     * ("hasAuthority('test')") // 具有 test 权限才能访问此接口
     */
    @RequestMapping("/test/hello")
    //@PreAuthorize("hasAuthority('system:dept:list')")
//    @PreAuthorize("@ex.hasAuthority('system:dept:list')")
    public String hello() {
        return "hello";
    }



    @RequestMapping("/testCors")
    public ResponseResult testCors() {
        return new ResponseResult(200, "testCors");
    }
}
