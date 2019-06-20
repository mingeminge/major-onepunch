package com.yzm.majorserver.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 14:38 2019/5/13
 * ===========================
 */
@Data
public class UpdatePassword {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
