package com.yzm.majorserver.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzm.majorserver.web.dataobject.RoleDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:47 2019/4/21
 * ===========================
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 549202301191509652L;
    private Integer id;

    private String username;

    private Integer sex;

    private String phone;

    private String email;

    private String imgUrl;

    private String nickName;

    private String signature;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<RoleDO> roles;
}
