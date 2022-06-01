package com.cxy.assessservice.api;

import com.cxy.commonutils.common.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-checkoff")
@Component
public interface CheckClientApi {

    @GetMapping("/checkoff/checkFormat/getUnqualifiedDetails/{page}/{pageSize}")
    public R getUnqualifiedDetails(@PathVariable("page") Integer page,
                                   @PathVariable("pageSize") Integer pageSize);

    @GetMapping("/checkoff/checkFormat/getUnqualifiedPacketCount")
    public R getUnqualifiedPacketCount();
}
