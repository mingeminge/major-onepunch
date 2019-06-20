package com.yzm.majorserver.web.query;

import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:29 2019/6/19
 * ===========================
 */
@Data
public class UserQuery {
    private Integer page = 1;

    private Integer limit = 10;

    private String username;

    private String phone;

    private String email;

    private String startTime;

    private String endTime;
}
