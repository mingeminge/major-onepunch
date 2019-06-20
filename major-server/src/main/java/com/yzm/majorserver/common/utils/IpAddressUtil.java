package com.yzm.majorserver.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzm.majorserver.common.vo.IpAddressResponse;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:13 2019/6/11
 * ===========================
 */
public class IpAddressUtil {

    public static IpAddressResponse getIpInfo(String ip) {
        String url = "https://way.jd.com/Quxun/ipaddress?ipaddress=" + ip + "&appkey=278782cf0c1a0442af27ed86d297ca03";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String resp = EntityUtils.toString(response.getEntity());
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
