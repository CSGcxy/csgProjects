package com.cxy.security.filter;

import com.alibaba.fastjson.JSON;
import com.cxy.commonutils.common.ResponseResult;
import com.cxy.commonutils.enums.AppHttpCodeEnum;
import com.cxy.commonutils.utils.JwtUtil;
import com.cxy.commonutils.utils.RedisCache;
import com.cxy.commonutils.utils.WebUtils;
import com.cxy.security.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 认证过滤器
 * @author bing_  @create 2022/1/5-14:12
 * 继承 OncePerRequestFilter 保证请求经过过滤器一次
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token ( 前端，用户登录后，将 token 放到请求头当中。所以这里从请求头中获取 token )
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 如果请求头没有 token ，放行
            //后面做的都是对token进行解析，不需要抛异常是因为后续还有过滤器，判断是否为认证状态
            filterChain.doFilter(request, response);
            return;
        }

        // token 不为空，解析 token
        String uesrId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            uesrId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Token有误：：" + e.toString());
            ResponseResult result = null;
            //token已经过期或token非法，响应告诉前端需要重新登录
            if (e instanceof ExpiredJwtException) {
                result = ResponseResult.errorResult(AppHttpCodeEnum.Token_LOGIN);
            } else {
                result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        // 从 redis 中获取用户信息
        String redisKey = "login:" + uesrId;

        LoginUser loginUser = redisCache.getCacheObject(redisKey);

        if (Objects.isNull(loginUser)) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.EXPIRE_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        // 将用户信息存入 SecurityContextHolder
        // 获取权限信息封装到 Authentication 中
        // 参数：用户信息、已认证状态、权限信息
        //TODO:获取用户权限信息，并封装到authenticationToken中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, token, null);
//                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request, response);
    }
}
