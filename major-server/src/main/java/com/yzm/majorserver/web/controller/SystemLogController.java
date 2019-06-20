package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.web.query.SystemLogQuery;
import com.yzm.majorserver.web.service.SystemLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:30 2019/6/8
 * ===========================
 */
@RestController
@RequestMapping("/systemLog")
public class SystemLogController {
    private static final String MODULE_NAME = "日志管理";

    private SystemLogServiceImpl systemLogService;

    @Autowired
    public SystemLogController(SystemLogServiceImpl systemLogService) {
        this.systemLogService = systemLogService;
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasPermission('sysLog','get')")
    public TableResult findAll(SystemLogQuery query) {
        return systemLogService.findAll(query);
    }

    @PostMapping("/deleteLog/{id}")
    @PreAuthorize("hasPermission('sysLog','delete')")
    public ResultVO deleteLog(@PathVariable Integer id) {
        Integer delete = systemLogService.delete(id);
        if (0 != delete) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }
}
