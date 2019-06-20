package com.yzm.majorserver.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.utils.Base64Util;
import com.yzm.majorserver.common.utils.JsonUtils;
import com.yzm.majorserver.common.utils.RedisUtil;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.web.dataobject.UserDO;
import com.yzm.majorserver.web.dto.UserRole;
import com.yzm.majorserver.web.dto.UserDTO;
import com.yzm.majorserver.web.dataobject.RoleDO;
import com.yzm.majorserver.core.exception.MajorException;
import com.yzm.majorserver.web.mapper.RoleMapper;
import com.yzm.majorserver.web.mapper.UserMapper;
import com.yzm.majorserver.web.dto.UserInfo;
import com.yzm.majorserver.web.query.UserQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author : yizuomin
 * @Description :  三流码农写UI 二流码农写架构 一流码农写算法 顶级码农穿女装
 * @Date : Created in 16:03 2019/4/11
 * @Modified By :
 */
@Component
public class UserServiceImpl implements UserDetailsService {

    private RedisUtil redisUtil;

    private UserMapper userMapper;

    private RoleMapper roleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper, RedisUtil redisUtil) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userMapper.findByUsername(username);
        if (null == userDO) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDO.getRoles().forEach(role -> {
            role.getAuthorities().forEach(authority -> {
                authorities.add(new SimpleGrantedAuthority(authority.getAuthorityUrl()));
            });
        });
        userDO.setGrantedAuthority(authorities);
        return userDO;
    }

    public UserDTO findById(Integer userId) {
        UserDO byId = userMapper.findById(userId);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(byId, userDTO);
        return userDTO;
    }

    public TableResult list(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPage(), userQuery.getLimit());
        List<UserDO> all = userMapper.findAll(userQuery);
        PageInfo<UserDO> pageInfo = new PageInfo<>(all);
        List<UserDTO> userDTOS = new ArrayList<>();
        all.forEach(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOS.add(userDTO);
        });
        return TableResult.success(pageInfo.getTotal(), userDTOS);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(UserDO userDO) {
        String password = Base64Util.getFromBase64(userDO.getPassword());
        userDO.setPassword(password);
        String encode = new BCryptPasswordEncoder().encode(userDO.getPassword());
        userDO.setPassword(encode);
        userDO.setCreateTime(new Date());
        userDO.setUpdateTime(new Date());
        return userMapper.save(userDO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer update(UserDO updateUserDO) {
        if (1 == updateUserDO.getId()) {
            throw new MajorException(ResultEnum.CAN_NOT_UPDATE);
        }
        UserDO userDO = userMapper.findById(updateUserDO.getId());
        if (null == userDO) {
            throw new MajorException(ResultEnum.ROLE_NOT_FOUND);
        }
        List<UserRole> userRoleList = new ArrayList<>();
        if (null != updateUserDO.getRoles() && updateUserDO.getRoles().size() > 0) {
            updateUserDO.getRoles().forEach(role -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userDO.getId());
                userRole.setRoleId(role.getId());
                userRoleList.add(userRole);
            });
            if (userRoleList.size() > 0) {
                userMapper.deleteUserRole(userDO.getId());
                userMapper.saveUserRole(userRoleList);
            }
        } else {
            userMapper.deleteUserRole(userDO.getId());
        }
        updateUserDO.setUpdateTime(new Date());
        return userMapper.update(updateUserDO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Integer userId) {
        if (1 == userId) {
            throw new MajorException(ResultEnum.CAN_NOT_UPDATE);
        }
        UserDO byId = userMapper.findById(userId);
        if (null == byId) {
            throw new MajorException(ResultEnum.USER_NOT_FOUND);
        }
        userMapper.deleteUserRole(userId);
        return userMapper.delete(userId);
    }

    public List<RoleDO> roleList() {
        return roleMapper.roleList();
    }

    public UserInfo userInfo(String token) {
        String json = redisUtil.get(token);
        if (null == json || "".equals(json)) {
            return null;
        }
        return JsonUtils.jsonToPojo(json, UserInfo.class);
    }
}