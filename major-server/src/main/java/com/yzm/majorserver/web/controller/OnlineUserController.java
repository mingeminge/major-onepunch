package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.core.annotation.LogAnnotation;
import com.yzm.majorserver.web.dto.UserInfo;
import com.yzm.majorserver.web.service.OnlineUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:55 2019/6/12
 * ===========================
 */
@RestController
@RequestMapping("/onlineUser")
public class OnlineUserController {

    private static final String MODULE_NAME = "在线用户";

    private OnlineUserServiceImpl onlineUserService;

    @Autowired
    public OnlineUserController(OnlineUserServiceImpl onlineUserService) {
        this.onlineUserService = onlineUserService;
    }

    @GetMapping("/list")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "列表")
    @PreAuthorize("hasPermission('sysOnlineUser','get')")
    public ResultVO getOnlineUsers() {
        List<UserInfo> aliveUsers = onlineUserService.getAliveUsers();
        return ResultVO.success(aliveUsers);
    }

    @PostMapping("/kill/{key}")
    @PreAuthorize("hasPermission('sysOnlineUser','delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "强制下线")
    public ResultVO killAliveUser(@PathVariable String key) {
        if (onlineUserService.killAliveUser(key)) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }
}
