package com.yzm.majorserver.web.service;

import com.yzm.majorserver.common.properties.TokenProperties;
import com.yzm.majorserver.common.utils.JsonUtils;
import com.yzm.majorserver.common.utils.RedisUtil;
import com.yzm.majorserver.web.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:44 2019/6/12
 * ===========================
 */
@Service
public class OnlineUserServiceImpl {

    private RedisUtil redisUtil;

    @Autowired
    private OnlineUserServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public List<UserInfo> getAliveUsers() {
        Set<String> keys = redisUtil.keys(TokenProperties.TOKEN_PREFIX + "*");
        List<UserInfo> aliveUserList = null;
        if (keys != null && keys.size() > 0) {
            aliveUserList = new ArrayList<>();
            for (String key : keys) {
                String o = redisUtil.get(key);
                UserInfo userInfo = JsonUtils.jsonToPojo(o, UserInfo.class);
                aliveUserList.add(userInfo);
            }
        }
        return aliveUserList;
    }

    public Boolean killAliveUser(String key) {
        return redisUtil.deleteKey(key);
    }

}
