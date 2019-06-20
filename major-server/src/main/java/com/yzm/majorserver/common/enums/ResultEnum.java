package com.yzm.majorserver.common.enums;

import lombok.Getter;

/**
 * @Author : yizuomin
 * @Description :  三流码农写UI 二流码农写架构 一流码农写算法 顶级码农穿女装
 * @Date : Created in 15:12 2019/3/21
 * @Modified By :
 */

@Getter
public enum ResultEnum {
    //成功
    SUCCESS(200, "成功"),
    //未登录
    NOT_LOGIN(201, "未登录"),
    //登出成功
    LOGIN_FAILED(202, "登录失败"),
    //登出失败
    LOGOUT_SUCCESS(203, "登出成功"),
    //密码不正确
    USERNAME_PASSWORD_ERROR(204, "用户名或密码错误"),
    //身份信息过期
    IDENTITY_EXPIRED(205, "身份信息过期,请重新登录"),
    //身份信息不合法
    IDENTITY_ILLEGAL(206, "身份信息不合法"),
    //用户不存在
    USER_NOT_FOUND(207, "该用户不存在"),
    //角色不存在
    ROLE_NOT_FOUND(210, "该角色不存在"),
    //操作失败
    FAILED(208, "操作失败"),
    //密码错误
    PASSWORD_ERROR(209, "密码错误"),
    //无数据
    NOT_FOUND(404, "无数据"),
    //无权限
    FORBIDDEN(403, "对不起您没有权限,若新分配权限,从新登录即可"),
    //服务器异常
    ERROR(500, "服务器异常"),
    //无法修改超级管理员
    CAN_NOT_UPDATE(501, "演示环境超级管理员信息无法修改,若您是超级管理员，修改其他信息请到个人中心页面自行修改"),
    //无法删除超级管理员
    CAN_NOT_DELETE(502, "超级管理员为最高权限，无法删除，请联系开发人"),
    //文件上传失败
    FILE_UPLOAD_FAILED(503, "文件上传失败"),
    //不是图片
    IS_NOT_IMAGES(504, "该文件不是图片类型");
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;


    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
