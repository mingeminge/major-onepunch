package com.yzm.majorserver.core.handler;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.core.exception.MajorException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 14:17 2019/5/13
 * ===========================
 */
@RestControllerAdvice
public class MajorExceptionHandler {

    @ExceptionHandler(MajorException.class)
    public ResultVO userException(MajorException e) {
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO exception(Exception e) {
        e.printStackTrace();
        return ResultVO.error(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResultVO accessDenied(AccessDeniedException e) {
        return ResultVO.error(ResultEnum.FORBIDDEN);
    }
}
