package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:49 2019/5/13
 * ===========================
 */
@Mapper
@Repository
public interface PersonalMapper {

    /**
     * 查询用户信息
     *
     * @param username 用户名
     * @return
     */
    UserDO findByUsername(String username);


    /**
     * 修改用户的基本信息
     *
     * @param userDO 用户信息
     * @return
     */
    Integer updateInfo(UserDO userDO);

    /**
     * 修改用户的信息
     *
     * @param userDO 用户信息
     * @return
     */
    Integer updatePassword(UserDO userDO);


    /**
     * 修改头像
     *
     * @param imgUrl
     * @param userId
     * @return
     */
    Integer updateImg(@Param("imgUrl") String imgUrl, @Param("userId") Integer userId);
}
