package com.yzm.majorserver.core.security;

import com.yzm.majorserver.common.enums.RoleEnum;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 09:35 2019/6/9
 * ===========================
 */
@Component
public class AuthPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (RoleEnum.ROLE_LOGIN.getRoleName().equals(authority.getAuthority())) {
                return false;
            }
            String[] split = authority.getAuthority().split(":");
            String first = split[0];
            String last = split[1];
            if (targetDomainObject.equals(first) && last.equals(permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
