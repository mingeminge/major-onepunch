package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.utils.ErrorMessageUtil;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.core.annotation.LogAnnotation;
import com.yzm.majorserver.web.dataobject.UserDO;
import com.yzm.majorserver.web.dto.UpdatePassword;
import com.yzm.majorserver.web.dto.UserDTO;
import com.yzm.majorserver.web.service.PersonalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:47 2019/5/13
 * ===========================
 */
@RestController
@RequestMapping("/personal")
public class PersonalController {

    private static final String MODULE_NAME = "个人中心";

    private PersonalServiceImpl personalService;

    @Autowired
    public PersonalController(PersonalServiceImpl personalService) {
        this.personalService = personalService;
    }

    @GetMapping("/findByUsername/{username}")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查询个人信息")
    public ResultVO findByUsername(@PathVariable String username) {
        UserDTO byUsername = personalService.findByUsername(username);
        return ResultVO.success(byUsername);
    }

    @PostMapping("/updateInfo")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改个人信息")
    public ResultVO updateInfo(@RequestBody UserDO userDO) {
        personalService.updateInfo(userDO);
        return ResultVO.success();
    }

    @PostMapping("/updatePassword")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改个人密码")
    public ResultVO updatePassword(@Validated @RequestBody UpdatePassword password, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        personalService.updatePassword(password.getUsername(), password.getOldPassword(), password.getNewPassword());
        return ResultVO.success();
    }

    @PostMapping("/updateImg")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改头像")
    public ResultVO updateImg(@RequestBody UserDTO userDTO) {
        personalService.updateImg(userDTO);
        return ResultVO.success();
    }
}
