package com.yzm.majorserver.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:16 2019/6/8
 * ===========================
 */
@Data
public class IndexInfo {

    private String roleName;

    private String description;

    private String nickName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginDate;

    private Integer loginCount;

    private Integer loginCountToday;

    private String region;

    private String isp;

    private String country;

    private String city;
}
