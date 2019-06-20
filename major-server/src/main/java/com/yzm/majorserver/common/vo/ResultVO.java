package com.yzm.majorserver.common.vo;

import com.yzm.majorserver.common.enums.ResultEnum;
import lombok.Data;

/**
 * @Author : yizuomin
 * @Description :  三流码农写UI 二流码农写架构 一流码农写算法 顶级码农穿女装
 * @Date : Created in 17:30 2019/3/28
 * @Modified By :
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String message;

    private T data;

    public static ResultVO success(Object data) {
        ResultVO restResult = new ResultVO();
        restResult.setCode(ResultEnum.SUCCESS.getCode());
        restResult.setMessage(ResultEnum.SUCCESS.getMessage());
        restResult.setData(data);
        return restResult;
    }

    public static ResultVO success() {
        ResultVO restResult = new ResultVO();
        restResult.setCode(ResultEnum.SUCCESS.getCode());
        restResult.setMessage(ResultEnum.SUCCESS.getMessage());
        return restResult;
    }

    public static ResultVO error(String msg) {
        ResultVO restResult = new ResultVO();
        restResult.setCode(ResultEnum.ERROR.getCode());
        restResult.setMessage(msg);
        return restResult;
    }

    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO restResult = new ResultVO();
        restResult.setCode(resultEnum.getCode());
        restResult.setMessage(resultEnum.getMessage());
        return restResult;
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO restResult = new ResultVO();
        restResult.setCode(code);
        restResult.setMessage(message);
        return restResult;
    }
}
