package com.yzm.majorserver.web.controller;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.utils.ErrorMessageUtil;
import com.yzm.majorserver.common.vo.ResultVO;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.core.annotation.LogAnnotation;
import com.yzm.majorserver.web.dataobject.ImageDO;
import com.yzm.majorserver.web.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:24 2019/6/14
 * ===========================
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    private ImageServiceImpl imageService;

    private static final String MODULE_NAME = "头像管理";

    @Autowired
    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasPermission('sysImage','get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public TableResult list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "24") Integer limit, Integer type) {
        return imageService.list(page, limit, type);
    }

    @PostMapping("/update")
    @PreAuthorize("hasPermission('sysImage','update')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "编辑")
    public ResultVO update(@Validated @RequestBody ImageDO imageDO, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        Integer update = imageService.update(imageDO);
        if (null != update && 0 != update) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasPermission('sysImage','delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    public ResultVO delete(@PathVariable Integer id) {
        Integer delete = imageService.delete(id);
        if (null != delete && 0 != delete) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasPermission('sysImage','upload')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "上传")
    public ResultVO upload(MultipartFile file) {
        String path = imageService.upload(file);
        if (null != path) {
            return ResultVO.success(path);
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

    @PostMapping("/save")
    @PreAuthorize("hasPermission('sysImage','upload')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "保存图片信息到数据库")
    public ResultVO save(@Validated @RequestBody ImageDO imageDO,BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(ErrorMessageUtil.getErrorMsg(result.getAllErrors()));
        }
        Integer save = imageService.save(imageDO);
        if (null != save && 0 != save) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.FAILED);
    }

}
