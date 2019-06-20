package com.yzm.majorserver.web.service;

import com.yzm.majorserver.web.dto.IndexInfo;
import com.yzm.majorserver.web.dto.VisitChart;
import com.yzm.majorserver.web.dataobject.UserDO;
import com.yzm.majorserver.web.dataobject.VisitLogDO;
import com.yzm.majorserver.web.mapper.UserMapper;
import com.yzm.majorserver.web.mapper.VisitLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:18 2019/6/8
 * ===========================
 */
@Service
public class VisitServiceImpl {

    private VisitLogMapper visitLogMapper;

    private UserMapper userMapper;

    @Autowired
    public VisitServiceImpl(VisitLogMapper visitLogMapper, UserMapper userMapper) {
        this.visitLogMapper = visitLogMapper;
        this.userMapper = userMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(VisitLogDO visitLogDO) {
        return visitLogMapper.save(visitLogDO);
    }

    public IndexInfo getInfo(String username) {
        IndexInfo info = new IndexInfo();
        Integer visitCount = visitLogMapper.getVisitCount();
        Integer visitToday = visitLogMapper.getVisitToday();

        UserDO byUsername = userMapper.findByUsername(username);
        List<Date> lastLoginDate = visitLogMapper.getLastLoginDate(byUsername.getId());

        if (null != lastLoginDate && lastLoginDate.size() > 0) {
            if (lastLoginDate.size() > 2) {
                info.setLastLoginDate(lastLoginDate.get(1));
            } else {
                info.setLastLoginDate(lastLoginDate.get(0));
            }
        }
        List<VisitLogDO> loginInfo = visitLogMapper.getLoginInfo(byUsername.getId());
        if (null != loginInfo && loginInfo.size() > 0) {
            if (loginInfo.size() > 2) {
                VisitLogDO visitLogDO = loginInfo.get(1);
                info.setCity(visitLogDO.getCity());
                info.setCountry(visitLogDO.getCountry());
                info.setIsp(visitLogDO.getIsp());
                info.setRegion(visitLogDO.getRegion());
            } else {
                VisitLogDO visitLogDO = loginInfo.get(0);
                info.setCity(visitLogDO.getCity());
                info.setCountry(visitLogDO.getCountry());
                info.setIsp(visitLogDO.getIsp());
                info.setRegion(visitLogDO.getRegion());
            }
        }
        info.setLoginCount(visitCount);
        info.setLoginCountToday(visitToday);
        info.setNickName(byUsername.getNickName());
        if (null != byUsername.getRoles() && byUsername.getRoles().size() > 0) {
            info.setRoleName(byUsername.getRoles().get(0).getRoleName());
            info.setDescription(byUsername.getRoles().get(0).getDescription());
        }
        return info;
    }

    public List<VisitChart> getLoginChart(String username) {
        UserDO byUsername = userMapper.findByUsername(username);
        return visitLogMapper.getLoginChart(byUsername.getId());
    }
}
