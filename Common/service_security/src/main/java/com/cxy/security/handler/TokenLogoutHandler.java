package com.cxy.security.handler;

import com.cxy.commonutils.common.R;
import com.cxy.commonutils.common.ResponseUtil;
import com.cxy.commonutils.utils.JwtUtil;
import com.cxy.commonutils.utils.RedisCache;
import com.cxy.security.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenLogoutHandler implements LogoutHandler {

    private RedisCache redisCache;

    public TokenLogoutHandler(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        Long userId = loginUser.getUser().getId();
        authenticationToken.eraseCredentials();
        //删除redis中的值
        redisCache.deleteObject("login:" + userId);
        ResponseUtil.out(response, R.ok().message("退出成功！"));
    }
}
