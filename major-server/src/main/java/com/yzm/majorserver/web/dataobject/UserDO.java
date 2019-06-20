package com.yzm.majorserver.web.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author : yizuomin
 * @date : Created in 15:57 2019/4/11
 */
@Data
public class UserDO implements UserDetails, Serializable {

    private static final long serialVersionUID = -7593316976318182376L;

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotNull(message = "性别不能为空")
    private Integer sex;

    @Length(max = 11, min = 11, message = "手机号不合法")
    private String phone;

    @Email(message = "非法的邮箱格式")
    private String email;

    @NotBlank(message = "昵称不能为空")
    @Length(max = 20, min = 2, message = "昵称长度为2-20个字符")
    private String nickName;

    private String imgUrl;

    @Length(max = 255, message = "长度在255个字符内")
    private String signature;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<RoleDO> roles;

    @Transient
    private Collection<? extends GrantedAuthority> grantedAuthority;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
