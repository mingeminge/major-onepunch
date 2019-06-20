package com.yzm.majorserver.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzm.majorserver.common.vo.IpAddressResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:13 2019/6/11
 * ===========================
 */
@Component
public class IpAddressUtil {

    @Value("${IpApi.url}")
    private String apiUrl;

    @Value("${IpApi.appKey}")
    private String appKey;

    public IpAddressResponse getIpInfo(String ip) {
        Map<String, String> map = new HashMap<>();
        map.put("ipaddress", ip);
        map.put("appkey", appKey);
        try {
            String resp = HttpClientUtil.doGet(apiUrl, map);
            JSONObject jsonObject = JSON.parseObject(resp);
            JSONObject result = jsonObject.getJSONObject("result");

            JSONObject data = result.getJSONObject("data");
            String city = data.getString("address_city");
            String region = data.getString("address_province");
            String isp = data.getString("address_isp");
            String country = data.getString("address_country");
            IpAddressResponse addressResponse = new IpAddressResponse();
            addressResponse.setCity(city);
            addressResponse.setRegion(region);
            addressResponse.setCountry(country);
            addressResponse.setIsp(isp);
            System.out.println(addressResponse);
            return addressResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
