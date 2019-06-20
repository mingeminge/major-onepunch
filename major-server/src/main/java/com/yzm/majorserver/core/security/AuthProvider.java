package com.yzm.majorserver.core.security;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.utils.Base64Util;
import com.yzm.majorserver.web.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author : yizuomin
 * @date : Created in 15:56 2019/4/11
 */
@Component
@Slf4j
public class AuthProvider implements AuthenticationProvider {

    private UserServiceImpl userService;

    @Autowired
    public AuthProvider(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String pwd = Base64Util.getFromBase64(password);
        UserDetails userInfo = userService.loadUserByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(ResultEnum.USER_NOT_FOUND.getMessage());
        }

        if (!new BCryptPasswordEncoder().matches(pwd, userInfo.getPassword())) {
            log.error("用户{},登录失败，原因：密码不正确", username);
            throw new BadCredentialsException("密码错误");
        }

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userInfo, pwd, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
