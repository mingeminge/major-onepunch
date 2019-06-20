package com.yzm.majorserver.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzm.majorserver.common.properties.TokenProperties;
import com.yzm.majorserver.common.utils.RedisUtil;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : yizuomin
 * @date : Created in 15:51 2019/4/11
 */
@Component
@Slf4j
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            redisUtil.deleteKey(request.getHeader(TokenProperties.TOKEN_HEADER));
        } catch (Exception e) {
            log.error("删除key失败");
        } finally {
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(ResultVO.success(ResultEnum.LOGOUT_SUCCESS)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
