package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.web.dto.IndexInfo;
import com.yzm.majorserver.web.dto.VisitChart;
import com.yzm.majorserver.web.service.VisitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 13:07 2019/6/8
 * ===========================
 */
@RestController
@RequestMapping("/visitLog")
public class VisitLogController {

    private VisitServiceImpl visitService;

    @Autowired
    public VisitLogController(VisitServiceImpl visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/info/{username}")
    public ResultVO getInfo(@PathVariable String username) {
        IndexInfo info = visitService.getInfo(username);
        return ResultVO.success(info);
    }

    @GetMapping("/getLoginChart/{username}")
    public ResultVO getLoginChart(@PathVariable String username) {
        List<VisitChart> loginChart = visitService.getLoginChart(username);
        return ResultVO.success(loginChart);
    }
}
