package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.UserDO;
import com.yzm.majorserver.web.dto.UserRole;
import com.yzm.majorserver.web.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:51 2019/4/23
 * ===========================
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 查询用户
     *
     * @param username 用户名
     * @return
     */
    UserDO findByUsername(String username);

    /**
     * 查询所有
     *
     * @param query 查询条件
     * @return
     */
    List<UserDO> findAll(UserQuery query);


    /**
     * 通过id查询
     *
     * @param id 用户id
     * @return
     */
    UserDO findById(Integer id);

    /**
     * 修改
     *
     * @param userDO
     * @return
     */
    Integer update(UserDO userDO);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 添加
     *
     * @param userDO
     * @return
     */
    Integer save(UserDO userDO);

    /**
     * 给用户添加角色
     *
     * @param userRoles
     * @return
     */
    void saveUserRole(@Param("userRoles") List<UserRole> userRoles);

    /**
     * 删除用户对应的角色关联
     *
     * @param userId 用户id
     * @return
     */
    Integer deleteUserRole(Integer userId);

    /**
     * 删除用户的角色关联
     *
     * @param roleIds 角色id集合
     * @param userId  用户id
     * @return
     */
    Integer deleteUserRoleByRoleId(@Param("roleIds") List<Integer> roleIds, @Param("userId") Integer userId);
}
