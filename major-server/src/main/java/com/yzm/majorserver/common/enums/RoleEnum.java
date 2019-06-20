package com.yzm.majorserver.common.enums;

import lombok.Getter;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:50 2019/6/17
 * ===========================
 */
@Getter
public enum RoleEnum {

    //登录角色，系统未分配角色
    ROLE_LOGIN("ROLE_LOGIN");

    /**
     * 角色名
     */
    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
