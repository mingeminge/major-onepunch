package com.yzm.majorserver.core.aop;

import com.yzm.majorserver.core.annotation.LogAnnotation;
import com.yzm.majorserver.web.dataobject.SystemLogDO;
import com.yzm.majorserver.web.service.SystemLogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author : yizuomin
 * @date : Created in 10:46 2019/3/29
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private long startTime;

    @Autowired
    private SystemLogServiceImpl systemLogService;

    @Pointcut("@annotation(com.yzm.majorserver.core.annotation.LogAnnotation)")
    public void logPointcut() {

    }

    @Before(value = "logPointcut()")
    public void before() {
        startTime = System.currentTimeMillis();
    }

    @AfterReturning(value = "logPointcut()&&args(object,..)&&@annotation(annotation)")
    public void afterReturn(JoinPoint joinPoint, Object object, LogAnnotation annotation) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.info("<===============================================>");
        log.info("用户:{}", username);
        log.info("模块名称:{}", annotation.moduleName());
        log.info("方法名称:{}", annotation.methodName());
        log.info("URL:{}", request.getRequestURI());
        log.info("METHOD:{}", request.getMethod());
        log.info("IP_ADDRESS:{}", request.getRemoteAddr());
        log.info("耗时:{}", (System.currentTimeMillis() - startTime));

        SystemLogDO systemLogDO = new SystemLogDO();
        systemLogDO.setUsername(username);
        systemLogDO.setModuleName(annotation.moduleName());
        systemLogDO.setMethodName(annotation.methodName());
        systemLogDO.setRequestUrl(request.getRequestURI());
        systemLogDO.setIpAddress(request.getRemoteHost());
        systemLogDO.setUseTime(System.currentTimeMillis() - startTime);
        systemLogDO.setCreateTime(new Date());
        systemLogDO.setIsError(false);
        systemLogService.saveLog(systemLogDO);
    }


    @AfterThrowing(value = "logPointcut()&&args(object,..)&&@annotation(annotation)", throwing = "e")
    public void afterReturningMethod(Object object, LogAnnotation annotation, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.info("<===============================================>");
        log.error("用户:{}", username);
        log.error("模块名称:{}", annotation.moduleName());
        log.error("方法名称:{}", annotation.methodName());
        log.error("URL:{}", request.getRequestURI());
        log.error("METHOD:{}", request.getMethod());
        log.error("IP_ADDRESS:{}", request.getRemoteAddr());
        log.error("耗时:{}", (System.currentTimeMillis() - startTime));
        log.error("是否异常:{}", true);
        log.error("异常信息:{}", e.getMessage());

        SystemLogDO systemLogDO = new SystemLogDO();
        systemLogDO.setUsername(username);
        systemLogDO.setModuleName(annotation.moduleName());
        systemLogDO.setMethodName(annotation.methodName());
        systemLogDO.setRequestUrl(request.getRequestURI());
        systemLogDO.setIpAddress(request.getRemoteHost());
        systemLogDO.setUseTime(System.currentTimeMillis() - startTime);
        systemLogDO.setCreateTime(new Date());
        systemLogDO.setIsError(true);
        systemLogDO.setErrorMsg(e.getMessage());
        systemLogService.saveLog(systemLogDO);
    }
}
