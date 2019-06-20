package com.yzm.majorserver.common.vo;

import com.yzm.majorserver.common.enums.ResultEnum;
import lombok.Data;

/**
 * ==========================
 *
 * @Author : yizuomin
 * Date : Created in 08:47 2019/4/12
 * ===========================
 */
@Data
public class TableResult<T> {

    private Integer code;

    private String message;

    private Long count;

    private T data;

    public static TableResult success(Long count, Object data) {
        TableResult tableResult = TableResult.newInstance();
        tableResult.setCode(ResultEnum.SUCCESS.getCode());
        tableResult.setCount(count);
        tableResult.setMessage(ResultEnum.SUCCESS.getMessage());
        tableResult.setData(data);
        return tableResult;
    }

    public static TableResult error(String message){
        TableResult tableResult = TableResult.newInstance();
        tableResult.setData(null);
        tableResult.setMessage(message);
        tableResult.setCode(ResultEnum.NOT_FOUND.getCode());
        tableResult.setCount(0L);
        return tableResult;
    }

    public static TableResult newInstance() {
        return new TableResult();
    }
}
