package com.yzm.majorserver.web.dataobject;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author : yizuomin
 * @date : Created in 15:59 2019/4/11
 */
@Data
public class RoleDO implements Serializable {

    private static final long serialVersionUID = -4155509464928214932L;

    private Integer id;

    @NotBlank(message = "角色名不能为空")
    private String roleName;

    private String description;

    private Integer available;

    private List<AuthorityDO> authorities;

    private List<MenuDO> menus;
}
