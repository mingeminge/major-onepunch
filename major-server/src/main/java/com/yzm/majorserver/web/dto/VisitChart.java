package com.yzm.majorserver.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:38 2019/6/8
 * ===========================
 */
@Data
public class VisitChart {

    private Integer visitCount;

    private Integer userVisit;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;
}
