package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:21 2019/6/19
 * ===========================
 */
@RestController
public class NoLoginController {
    @GetMapping("/notLogin")
    public ResultVO notLogin() {
        return ResultVO.error(ResultEnum.NOT_LOGIN);
    }
}
