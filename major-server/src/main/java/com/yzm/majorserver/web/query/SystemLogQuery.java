package com.yzm.majorserver.web.query;

import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:32 2019/6/8
 * ===========================
 */
@Data
public class SystemLogQuery {

    private Integer page = 1;

    private Integer limit = 10;

    private String username;

    private String startTime;

    private String endTime;

    private Boolean isError;
}
