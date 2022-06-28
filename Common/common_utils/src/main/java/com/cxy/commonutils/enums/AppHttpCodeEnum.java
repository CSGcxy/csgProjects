package com.cxy.commonutils.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),

    //参数错误
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),


    // 登录
    NEED_LOGIN(400,"需要登录才能操作"),
    Token_LOGIN(401,"状态有误，请重新登录"),
    EXPIRE_LOGIN(402,"登录过期，需重新登录"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    NOT_POST_HANDLER(405,"登录请求不能用非POST请求"),
    NOT_POST_CATCHA(406,"验证码输入有误"),

    SYSTEM_ERROR(500,"服务器出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    BAD_REQUEST(506,"请求参数错误"),
    NICKNAME_EXIST(507,"昵称已存在"),
    NICKNAME_NO_EXIST(508,"用户不存在");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
