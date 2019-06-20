package com.yzm.majorserver.web.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : yizuomin
 * @date : Created in 17:09 2019/4/3
 */
@Data
public class VisitLogDO implements Serializable {

    private static final long serialVersionUID = -2341834876525346806L;

    private Integer id;

    private String ip;

    private Integer userId;

    private String region;

    private String isp;

    private String country;

    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;
}
