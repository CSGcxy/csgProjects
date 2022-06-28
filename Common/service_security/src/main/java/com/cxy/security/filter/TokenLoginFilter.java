package com.cxy.security.filter;

import com.alibaba.fastjson.JSON;
import com.cxy.commonutils.common.R;
import com.cxy.commonutils.common.ResponseResult;
import com.cxy.commonutils.common.ResponseUtil;
import com.cxy.commonutils.enums.AppHttpCodeEnum;
import com.cxy.commonutils.utils.JwtUtil;
import com.cxy.commonutils.utils.RedisCache;
import com.cxy.commonutils.utils.WebUtils;
import com.cxy.security.entity.LoginUser;
import com.cxy.security.entity.User;
import com.cxy.security.entity.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Objects;


@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private RedisCache redisCache;



    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisCache redisCache) {
        this.authenticationManager = authenticationManager;
        this.redisCache = redisCache;
//        this.setPostOnly(false);
        this.setPasswordParameter("username");
        this.setPasswordParameter("password");
//        super(new AntPathRequestMatcher("/manage/user/login","POST"));
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login"));
    }
//

    /**
     *  得到表单提交过来的用户名和密码，然后回去调用UserDetailsService实现接口进行认证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //登录表单只支持post,进行验证
        if(!"POST".equals(request.getMethod())){
            throw new AuthenticationServiceException(
                     "Authentication method not supported: " + request.getMethod()
            );
        }
        //验证码验证
        String captcha = (String) request.getSession().getAttribute("captcha");


        //获取表单提交数据
        try {
//            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            UserVO user = new ObjectMapper().readValue(request.getInputStream(), UserVO.class);
            String code = user.getCode();
            log.info("code:" + code);
            if(StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)){
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NOT_POST_CATCHA);
                WebUtils.renderString(response, JSON.toJSONString(result));
                throw new RuntimeException(result.getMsg());
            }
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录认证成功后会调用该方法
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if(Objects.isNull(authResult)){
            throw new RemoteException("用户名或密码错误");
        }
        //认证成功，则得到认证成功后用户的信息
        LoginUser loginUser = (LoginUser)authResult.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //认证成功后，根据用户ID生成token
        String jwt = JwtUtil.createJWT(userId);
        //存入redis中
        redisCache.setCacheObject("login:" + userId, loginUser);

        ResponseUtil.out(response, R.ok().message("登录成功").data("token", jwt));
    }

    /**
     * 登录认证失败会调用该方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, R.error().message(failed.getLocalizedMessage()));
    }
}
