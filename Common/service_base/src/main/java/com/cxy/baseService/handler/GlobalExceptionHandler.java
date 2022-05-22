package com.cxy.baseService.handler;

import com.cxy.commonutils.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理：让异常结果也显示为统一的返回结果对象，并且统一处理系统的异常信息
 */
@ControllerAdvice   //Spring的AOP切面编程原理
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }

    @ExceptionHandler({CsgException.class})
    @ResponseBody
    public R error(CsgException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
