package com.yzm.majorserver.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.enums.RoleEnum;
import com.yzm.majorserver.common.properties.TokenProperties;
import com.yzm.majorserver.common.utils.JsonUtils;
import com.yzm.majorserver.common.utils.RedisUtil;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.web.dto.UserInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author : yizuomin
 * @date : Created in 16:41 2019/4/11
 */

public class AuthTokenFilter extends BasicAuthenticationFilter {
    private RedisUtil redisUtil;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AuthTokenFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil) {
        super(authenticationManager);
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(TokenProperties.TOKEN_HEADER);
        response.setContentType("application/json;charset=UTF-8");
        if (token == null) {
            response.getWriter().write(objectMapper.writeValueAsString(ResultVO.error(ResultEnum.NOT_LOGIN)));
            return;
        }
        if (!token.startsWith(TokenProperties.TOKEN_PREFIX)) {
            response.getWriter().write(objectMapper.writeValueAsString(ResultVO.error(ResultEnum.IDENTITY_ILLEGAL)));
            return;
        }
        if (!redisUtil.hasKey(token)) {
            response.getWriter().write(objectMapper.writeValueAsString(ResultVO.error(ResultEnum.IDENTITY_EXPIRED)));
            return;
        }


        if (redisUtil.hasKey(token) && token.startsWith(TokenProperties.TOKEN_PREFIX)) {
            String userJson = redisUtil.get(token);
            UserInfo user = JsonUtils.jsonToPojo(userJson, UserInfo.class);
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            UsernamePasswordAuthenticationToken authenticationToken = null;
            if (user != null && user.getRoles().size() > 0) {
                user.getAuthorities().forEach(authority -> {
                    authorities.add(new SimpleGrantedAuthority(authority));
                });

                authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
                redisUtil.flushToken(token);
            } else if (user != null) {
                authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_LOGIN.getRoleName()));
                authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
            }
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        }

    }
}
