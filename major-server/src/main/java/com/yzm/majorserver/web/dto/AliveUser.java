package com.yzm.majorserver.web.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:11 2019/6/12
 * ===========================
 */
@Data
public class AliveUser {

    private String token;

    private String username;

    private String region;

    private String isp;

    private String country;

    private String city;

    private String ip;

    private Date loginTime;

    private List<String> roles;
}
