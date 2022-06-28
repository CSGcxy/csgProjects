package com.cxy.security.entity;

import lombok.Data;

@Data
public class UserVO {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;
}
