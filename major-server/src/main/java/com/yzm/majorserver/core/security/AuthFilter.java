package com.yzm.majorserver.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.properties.TokenProperties;
import com.yzm.majorserver.common.utils.IpAddressUtil;
import com.yzm.majorserver.common.utils.JsonUtils;
import com.yzm.majorserver.common.utils.RedisUtil;
import com.yzm.majorserver.common.vo.IpAddressResponse;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.web.dataobject.UserDO;
import com.yzm.majorserver.web.dataobject.VisitLogDO;
import com.yzm.majorserver.web.dto.UserInfo;
import com.yzm.majorserver.web.service.VisitServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:00 2019/4/13
 * ===========================
 */
@Slf4j
public class AuthFilter extends UsernamePasswordAuthenticationFilter {
    private ObjectMapper objectMapper = new ObjectMapper();

    private RedisUtil redisUtil;

    private AuthenticationManager authenticationManager;

    private VisitServiceImpl visitService;

    private IpAddressUtil ipAddressUtil;

    public AuthFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil, VisitServiceImpl visitService, IpAddressUtil ipAddressUtil) {
        this.authenticationManager = authenticationManager;
        this.redisUtil = redisUtil;
        this.visitService = visitService;
        this.ipAddressUtil = ipAddressUtil;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDO loginUserDO = new ObjectMapper().readValue(request.getInputStream(), UserDO.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDO.getUsername(), loginUserDO.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDO userDO = (UserDO) authResult.getPrincipal();
        userDO.setPassword(null);

        String key = TokenProperties.TOKEN_PREFIX + UUID.randomUUID().toString();
        UserInfo userInfo = new UserInfo();
        List<String> roleList = new ArrayList<>();
        List<String> authorities = new ArrayList<>();
        userDO.getRoles().forEach(role -> {
            roleList.add(role.getRoleName());
            role.getAuthorities().forEach(authority -> {
                authorities.add(authority.getAuthorityUrl());
            });
        });
        try {
            IpAddressResponse ipInfo = ipAddressUtil.getIpInfo(request.getRemoteAddr());
            if (null != ipInfo) {
                VisitLogDO visitLogDO = new VisitLogDO();
                BeanUtils.copyProperties(ipInfo, visitLogDO);
                visitLogDO.setUserId(userDO.getId());
                visitLogDO.setIp(request.getRemoteAddr());
                visitLogDO.setLoginDate(new Date());
                visitService.save(visitLogDO);
                userInfo.setCountry(ipInfo.getCountry());
                userInfo.setCity(ipInfo.getCity());
                userInfo.setRegion(ipInfo.getRegion());
                userInfo.setIsp(ipInfo.getIsp());
            }
        } catch (Exception e) {
            log.error("ip信息查询接口异常");
        } finally {
            userInfo.setIp(request.getRemoteAddr());
            userInfo.setToken(key);
            userInfo.setNickName(userDO.getNickName());
            userInfo.setUsername(userDO.getUsername());
            userInfo.setRoles(roleList);
            userInfo.setAuthorities(authorities);
            userInfo.setImgUrl(userDO.getImgUrl());
            userInfo.setLoginTime(new Date());

            String value = JsonUtils.objectToJson(userInfo);
            redisUtil.setToken(key, value);
            log.info("用户{}登录成功", userDO.getUsername());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResultVO.success(userInfo)));
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResultVO.error(ResultEnum.USERNAME_PASSWORD_ERROR)));
    }
}
