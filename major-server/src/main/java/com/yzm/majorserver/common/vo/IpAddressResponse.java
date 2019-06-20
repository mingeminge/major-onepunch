package com.yzm.majorserver.common.vo;

import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:11 2019/6/11
 * ===========================
 */
@Data
public class IpAddressResponse {

    private String region;

    private String isp;

    private String country;

    private String city;

}
