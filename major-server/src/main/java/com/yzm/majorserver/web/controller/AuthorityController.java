package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.utils.ErrorMessageUtil;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.core.annotation.LogAnnotation;
import com.yzm.majorserver.web.dataobject.AuthorityDO;
import com.yzm.majorserver.web.service.AuthorityServiceImpl;
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
 * @date : Created in 10:51 2019/6/9
 * ===========================
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private static final String MODULE_NAME = "权限管理";

    private AuthorityServiceImpl authorityService;

    @Autowired
    public AuthorityController(AuthorityServiceImpl authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('sysAuthority','get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public ResultVO findById(@PathVariable Integer id) {
        AuthorityDO byId = authorityService.findById(id);
        return ResultVO.success(byId);
    }

    @GetMapping("/list")
    @PreAuthorize("hasPermission('sysAuthority','get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "列表")
    public ResultVO findAll() {
        List<AuthorityDO> all = authorityService.findAll();
        return ResultVO.success(all);
    }

    @PostMapping("/save")
    @PreAuthorize("hasPermission('sysAuthority','add')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "添加")
    public ResultVO save(@Validated @RequestBody AuthorityDO authorityDO, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        authorityService.save(authorityDO);
        return ResultVO.success();
    }

    @PostMapping("/update")
    @PreAuthorize("hasPermission('sysAuthority','update')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改")
    public ResultVO update(@Validated @RequestBody AuthorityDO authorityDO,BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        authorityService.update(authorityDO);
        return ResultVO.success();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasPermission('sysAuthority','delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    public ResultVO delete(@PathVariable Integer id) {
        authorityService.delete(id);
        return ResultVO.success();
    }
}
