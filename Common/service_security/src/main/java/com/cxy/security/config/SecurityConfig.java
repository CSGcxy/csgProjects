package com.cxy.security.config;

import com.cxy.commonutils.utils.RedisCache;
import com.cxy.security.filter.JwtAuthenticationTokenFilter;
import com.cxy.security.filter.TokenLoginFilter;
import com.cxy.security.handler.TokenLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //登录认证出现异常处理器
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    //TODO:配置授权出现异常处理器

    //AuthenticationManager进行用户认证,并注入容器
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //使用BCryptPasswordEncoder进行密码校验
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/test/**")
                .antMatchers("/authenticate",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/webjars/**",
                        "/captcha/getCaptcha");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问；即已登录状态是不能访问的
//                .antMatchers(HttpMethod.POST,"/manage/user/login").anonymous()
                //permitAll()表示登录和未登录都可以访问
//                .antMatchers("/swagger-ui/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()//除上面的路径外的url，任意用户认证后都可以访问
                .and()
                .logout().logoutUrl("/user/logout")
                .addLogoutHandler(new TokenLogoutHandler(redisCache))
                .and()
                .addFilterAt(new TokenLoginFilter(authenticationManager(),redisCache),UsernamePasswordAuthenticationFilter.class)
                .httpBasic();

        //将token校验过滤器添加到过滤器链中
        //将过滤器jwtAuthenticationTokenFilter添加到UsernamePasswordAuthenticationFilter过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, LogoutFilter.class)
            .exceptionHandling()
                //自定义的认证失败统一处理，调用自定义UnauthorizedEntryPoint处理器
                //TODO:加一个自定义的授权失败统一处理 MyAccessDeniedHandler
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
