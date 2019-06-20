package com.yzm.majorserver.web.dataobject;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 01:03 2019/6/7
 * ===========================
 */
@Data
public class AuthorityDO implements Serializable {

    private static final long serialVersionUID = 670073548331983935L;

    private Integer id;

    @NotBlank(message = "权限名不能为空")
    @Length(max = 20, message = "权限名长度超过限制")
    private String authorityName;

    @Length(max = 100, message = "权限url长度超过限制")
    private String authorityUrl;

    private String remark;

    private Integer parentId;

    private List<AuthorityDO> children;
}
