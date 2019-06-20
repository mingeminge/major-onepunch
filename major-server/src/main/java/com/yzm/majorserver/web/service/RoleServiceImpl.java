package com.yzm.majorserver.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.web.dataobject.RoleDO;
import com.yzm.majorserver.core.exception.MajorException;
import com.yzm.majorserver.web.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:19 2019/4/12
 * ===========================
 */
@Service
public class RoleServiceImpl {

    private RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public RoleDO findById(Integer id) {
        return roleMapper.findById(id);
    }

    public TableResult findAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<RoleDO> all = roleMapper.findAll();
        PageInfo<RoleDO> info = new PageInfo<>(all);
        return TableResult.success(info.getTotal(), all);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer addRole(RoleDO roleDO) {
        return roleMapper.addRole(roleDO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer delRole(Integer id) {
        if (1 == id) {
            throw new MajorException(ResultEnum.CAN_NOT_DELETE);
        }
        roleMapper.deleteRoleAuthority(id);
        return roleMapper.delRole(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateRole(RoleDO updateRoleDO) {
        RoleDO byId = roleMapper.findById(updateRoleDO.getId());
        if (null == byId) {
            throw new MajorException(ResultEnum.USER_NOT_FOUND);
        }
        if (updateRoleDO.getAuthorities() != null && updateRoleDO.getAuthorities().size() > 0) {
            List<Integer> authorityIds = new ArrayList<>();
            updateRoleDO.getAuthorities().forEach(authority -> {
                authorityIds.add(authority.getId());
            });
            if (authorityIds.size() > 0) {
                roleMapper.deleteRoleAuthority(byId.getId());
                roleMapper.setRoleAuthority(byId.getId(), authorityIds);
            }
        } else {
            roleMapper.deleteRoleAuthority(byId.getId());
        }
        return roleMapper.updateRole(updateRoleDO);
    }

    public List<Integer> findAuthorities(Integer roleId) {
        return roleMapper.findAuthorities(roleId);
    }
}
