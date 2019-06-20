package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.utils.ErrorMessageUtil;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.core.annotation.LogAnnotation;
import com.yzm.majorserver.web.dataobject.RoleDO;
import com.yzm.majorserver.web.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 00:28 2019/6/8
 * ===========================
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private static final String MODULE_NAME = "角色管理";

    private RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查询单个角色")
    @PreAuthorize("hasPermission('sysRole','get')")
    public ResultVO findById(@PathVariable Integer id) {
        RoleDO byId = roleService.findById(id);
        if (null != byId) {
            return ResultVO.success(byId);
        }
        return ResultVO.error(ResultEnum.NOT_FOUND);
    }

    @GetMapping("/list")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查询角色列表")
    @PreAuthorize("hasPermission('sysRole','get')")
    public TableResult findAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return roleService.findAll(page, limit);
    }

    @PostMapping("/save")
    @PreAuthorize("hasPermission('sysRole','update')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改角色")
    public ResultVO addRole(@Validated @RequestBody RoleDO roleDO, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        Integer integer = roleService.addRole(roleDO);
        if (0 != integer) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @PostMapping("/delete/{roleId}")
    @PreAuthorize("hasPermission('sysRole','delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除角色")
    public ResultVO delRole(@PathVariable Integer roleId) {
        Integer integer = roleService.delRole(roleId);
        if (0 != integer) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @PostMapping("/update")
    @PreAuthorize("hasPermission('sysRole','update')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改角色")
    public ResultVO update(@Validated @RequestBody RoleDO roleDO,BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        Integer integer = roleService.updateRole(roleDO);
        if (0 != integer) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @GetMapping("/findAuthorities/{roleId}")
    public ResultVO findAuthorities(@PathVariable Integer roleId) {
        List<Integer> authorities = roleService.findAuthorities(roleId);
        return ResultVO.success(authorities);
    }
}
