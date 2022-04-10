package com.example.foodcommentadmin.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    /**
     * 通用异常
     */
    SUCCESS(true, 20000, "成功"),
    UNKNOWN_ERROR(false, 20001, "未知错误"),
    PARAM_ERROR(false, 20002, "参数错误"),
    NULL_POINT(false, 20003, "空指针异常"),
    HTTP_CLIENT_ERROR(false, 20004, "http client异常"),

    USERNAME_EXISTS(false, 30001, "用户名已存在!"),
    USER_NOT_EXIST(false, 30002, "用户不存在!");


    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultCode(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}

