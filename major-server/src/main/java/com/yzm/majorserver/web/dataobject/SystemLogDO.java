package com.yzm.majorserver.web.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:11 2019/6/8
 * ===========================
 */
@Data
public class SystemLogDO {

    private Integer id;

    private String username;

    private String moduleName;

    private String methodName;

    private String params;

    private String requestUrl;

    private String ipAddress;

    private Long useTime;

    private Boolean isError;

    private String errorMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
