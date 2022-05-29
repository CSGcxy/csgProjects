package com.cxy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cxy.baseService.exception.SysException;
import com.cxy.commonutils.enums.AppHttpCodeEnum;
import com.cxy.security.entity.LoginUser;
import com.cxy.security.entity.User;
import com.cxy.user.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * authenticationManager.authenticate(authenticationToken)方法使用时会调用该接口实现方法进行用户校验
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

//    @Autowired
//    private UserService userService;

//    @Resource
//    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new SysException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        // 根据用户名查询用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));
//        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));
        // 如果没有查询到用户就抛出异常
        //TODO:此处可以对用户的状态再筛选
        if (Objects.isNull(user)) {
//            throw new ProException(20002, "用户名或密码错误");
            throw new SysException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        // TODO 查询对应的权限信息,添加到LoginUser中
        // 写死测试固定权限
        // List<String> list = new ArrayList<>(Arrays.asList("test", "admin"));
//        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        // 把数据封装成 UserDetails 返回，参数：用户信息、权限列表，存放在Authentication中
        return new LoginUser(user);
    }
}
