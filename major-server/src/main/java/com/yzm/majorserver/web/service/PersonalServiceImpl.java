package com.yzm.majorserver.web.service;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.utils.Base64Util;
import com.yzm.majorserver.web.dto.UserDTO;
import com.yzm.majorserver.web.dataobject.UserDO;
import com.yzm.majorserver.core.exception.MajorException;
import com.yzm.majorserver.web.mapper.PersonalMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:49 2019/5/13
 * ===========================
 */
@Service
public class PersonalServiceImpl {

    @Value("${imgDirPath}")
    private String imgDirPath;

    private PersonalMapper personalMapper;

    @Autowired
    public PersonalServiceImpl(PersonalMapper personalMapper) {
        this.personalMapper = personalMapper;
    }

    public UserDTO findByUsername(String username) {
        UserDO byUsername = personalMapper.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(byUsername, userDTO);
        return userDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(UserDO userDO) {
        UserDO byUsername = personalMapper.findByUsername(userDO.getUsername());
        if (null == byUsername) {
            throw new MajorException(ResultEnum.USER_NOT_FOUND);
        }
        byUsername.setUpdateTime(new Date());
        Integer integer = personalMapper.updateInfo(userDO);
        if (null == integer || 0 == integer) {
            throw new MajorException(ResultEnum.FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String username, String oldPassword, String newPassword) {
        UserDO byUsername = personalMapper.findByUsername(username);
        if (null == byUsername) {
            throw new MajorException(ResultEnum.USER_NOT_FOUND);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String decodeOldPass = Base64Util.getFromBase64(oldPassword);
        String decodeNewPass=Base64Util.getFromBase64(newPassword);
        if (!passwordEncoder.matches(decodeOldPass, byUsername.getPassword())) {
            throw new MajorException(ResultEnum.PASSWORD_ERROR);
        }
        String newPass = passwordEncoder.encode(decodeNewPass);
        byUsername.setPassword(newPass);
        byUsername.setUpdateTime(new Date());
        Integer integer = personalMapper.updatePassword(byUsername);
        if (null == integer || 0 == integer) {
            throw new MajorException(ResultEnum.FAILED);
        }
    }

    public void updateImg(UserDTO userDTO) {
        UserDO byUsername = personalMapper.findByUsername(userDTO.getUsername());
        if (null == byUsername) {
            throw new MajorException(ResultEnum.USER_NOT_FOUND);
        }
        Integer integer = personalMapper.updateImg(userDTO.getImgUrl(), byUsername.getId());
        if (null == integer || 0 == integer) {
            throw new MajorException(ResultEnum.FAILED);
        }
    }
}
