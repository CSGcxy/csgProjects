package com.cxy.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author bing_  @create 2022/1/5-11:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

//    private User user;
//
//    private List<String> permissions;
//
//    public LoginUser(User user, List<String> permissions) {
//        this.user = user;
//        this.permissions = permissions;
//    }
//
//
//    /**
//     * 添加此注解 此属性不会被序列化到 redis 当中
//     */
//    @JSONField(serialize = false)
//    private List<SimpleGrantedAuthority> authorities;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        // 权限为空的时候才往遍历，不为空直接返回
//        if (authorities != null) {
//            return authorities;
//        }
//
//        //把permissions中String类型的权限信息封装成SimpleGrantedAuthority对象
//
//        // 遍历方式一
//        //authorities = new ArrayList<>();
//        // for (String permission : permissions) {
//        //     SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
//        //     authorities.add(authority);
//        // }
//
//        // 遍历方式二
//        authorities = permissions.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//        return authorities;
//    }

    private User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}