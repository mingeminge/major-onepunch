package com.yzm.majorserver.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 16:59 2019/4/19
 * ===========================
 */
@Data
public class UserInfo {

    private String token;

    private String username;

    private String nickName;

    private String imgUrl;

    private String region;

    private String isp;

    private String ip;

    private String country;

    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    private List<String> roles;

    private List<String> authorities;
}
