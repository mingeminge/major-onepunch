package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 00:28 2019/6/8
 * ===========================
 */
@Mapper
@Repository
public interface RoleMapper {

    /**
     * 通过id查询
     * @param id
     * @return
     */
    RoleDO findById(@Param("id") Integer id);

    /**
     * 列表
     *
     * @return
     */
    List<RoleDO> findAll();

    /**
     * 添加
     *
     * @param roleDO
     * @return
     */
    Integer addRole(RoleDO roleDO);

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    Integer delRole(@Param("roleId") Integer roleId);


    /**
     * 修改
     *
     * @param roleDO
     * @return
     */
    Integer updateRole(RoleDO roleDO);

    /**
     * 查询角色对应的权限
     *
     * @param roleId
     * @return
     */
    List<Integer> findAuthorities(@Param("roleId") Integer roleId);

    /**
     * 给角色添加权限
     *
     * @param roleId
     * @param authorityIds
     * @return
     */
    Integer setRoleAuthority(@Param("roleId") Integer roleId, @Param("authorityIds") List<Integer> authorityIds);

    /**
     * 删除角色对应的权限
     * @param roleId
     * @param authorityIds
     * @return
     */
    Integer deleteRoleAuthorityByRoleId(@Param("roleId")Integer roleId, @Param("authorityIds") List<Integer> authorityIds);

    /**
     * 删除角色对应的权限
     * @param roleId
     * @return
     */
    Integer deleteRoleAuthority(@Param("roleId")Integer roleId);

    /**
     * 角色列表
     *
     * @return
     */
    List<RoleDO> roleList();
}
