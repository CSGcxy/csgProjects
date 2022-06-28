package com.cxy.user.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码接口
 */
@Controller
@RequestMapping("/captcha")
@Slf4j
public class KaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "验证码", produces = "image/jpg;charset=UTF-8")
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //通过流的形式传输图片，设置请求头输出为image类型
        response.setContentType("image/jpg");
        // 生成验证码
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        response.setDateHeader("Expires", 0);
        //生成验证码
        String captcha = defaultKaptcha.createText();
        log.info("code-->" + captcha);
        request.getSession().setAttribute("captcha", captcha);
        BufferedImage image = defaultKaptcha.createImage(captcha);
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}
