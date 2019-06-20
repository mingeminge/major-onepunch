package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dto.VisitChart;
import com.yzm.majorserver.web.dataobject.VisitLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:31 2019/4/23
 * ===========================
 */
@Mapper
@Repository
public interface VisitLogMapper {

    /**
     * 添加
     *
     * @param visitLogDO 登录日志
     * @return
     */
    Integer save(VisitLogDO visitLogDO);

    /**
     * 获取访问量
     *
     * @return
     */
    Integer getVisitCount();

    /**
     * 今日访问量
     *
     * @return
     */
    Integer getVisitToday();


    /**
     * 查询用户的上次登录时间
     *
     * @param userId
     * @return
     */
    List<Date> getLastLoginDate(@Param("userId") Integer userId);

    /**
     * 首页登录统计
     *
     * @param userId
     * @return
     */
    List<VisitChart> getLoginChart(@Param("userId") Integer userId);

    /**
     * 获取登录信息
     * @param userId
     * @return
     */
    List<VisitLogDO>getLoginInfo(@Param("userId") Integer userId);
}
