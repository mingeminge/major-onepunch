package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.properties.TokenProperties;
import com.yzm.majorserver.common.utils.ErrorMessageUtil;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.core.annotation.LogAnnotation;
import com.yzm.majorserver.web.dataobject.UserDO;
import com.yzm.majorserver.web.dto.UserDTO;
import com.yzm.majorserver.web.dataobject.RoleDO;
import com.yzm.majorserver.web.dto.UserInfo;
import com.yzm.majorserver.web.service.UserServiceImpl;
import com.yzm.majorserver.web.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yizuomin
 * @date : Created in 16:34 2019/4/11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final String MODULE_NAME = "用户管理";

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/userInfo")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "个人信息")
    @PreAuthorize("hasPermission('sysUser','get')")
    public ResultVO userInfo(HttpServletRequest request) {
        String token = request.getHeader(TokenProperties.TOKEN_HEADER);
        UserInfo user = userService.userInfo(token);
        return ResultVO.success(user);
    }

    @GetMapping("/list")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "用户列表")
    @PreAuthorize("hasPermission('sysUser','get')")
    public TableResult list(UserQuery userQuery) {
        return userService.list(userQuery);
    }

    @GetMapping("/findById/{userId}")
    @PreAuthorize("hasPermission('sysUser','get')")
    public ResultVO findById(@PathVariable Integer userId) {
        UserDTO byId = userService.findById(userId);
        return ResultVO.success(byId);
    }

    @PostMapping("/save")
    @PreAuthorize("hasPermission('sysUser','add')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "添加用户")
    public ResultVO save(@RequestBody @Validated UserDO userDO, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        Integer save = userService.save(userDO);
        if (0 != save) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @PostMapping("/update")
    @PreAuthorize("hasPermission('sysUser','update')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改用户")
    public ResultVO update(@Validated @RequestBody UserDO userDO, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        Integer update = userService.update(userDO);
        if (update != 0) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasPermission('sysUser','delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除用户")
    public ResultVO delete(@PathVariable Integer id) {
        Integer delete = userService.delete(id);
        if (delete != 0) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @GetMapping("/roleList")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "所有角色列表")
    @PreAuthorize("hasPermission('sysUser','get')")
    public ResultVO roleList() {
        List<RoleDO> roleDOS = userService.roleList();
        return ResultVO.success(roleDOS);
    }
}
