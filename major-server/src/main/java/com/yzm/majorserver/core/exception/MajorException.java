package com.yzm.majorserver.core.exception;

import com.yzm.majorserver.common.enums.ResultEnum;
import lombok.Data;
import lombok.Getter;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 16:17 2019/4/23
 * ===========================
 */
@Getter
public class MajorException extends RuntimeException {

    private Integer code;

    private String message;

    public MajorException(ResultEnum resultEnum) {
        this.message = resultEnum.getMessage();
        this.code = resultEnum.getCode();
    }
}
