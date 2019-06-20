package com.yzm.majorserver.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.common.utils.FileUploadUtil;
import com.yzm.majorserver.common.vo.TableResult;
import com.yzm.majorserver.web.dataobject.ImageDO;
import com.yzm.majorserver.core.exception.MajorException;
import com.yzm.majorserver.web.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:07 2019/6/14
 * ===========================
 */
@Service
public class ImageServiceImpl {

    private ImageMapper imageMapper;


    @Value("${imgDirPath}")
    private String filePath;

    @Autowired
    public ImageServiceImpl(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    public TableResult list(Integer page, Integer limit, Integer type) {
        PageHelper.startPage(page, limit);
        List<ImageDO> list = imageMapper.list(type);
        PageInfo<ImageDO> info = new PageInfo<>(list);
        return TableResult.success(info.getTotal(), list);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer update(ImageDO imageDO) {
        return imageMapper.update(imageDO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Integer id) {
        ImageDO imageDO = imageMapper.findById(id);
        if (null == imageDO) {
            throw new MajorException(ResultEnum.NOT_FOUND);
        }
        File file = new File(filePath + imageDO.getImgUrl());
        if (file.exists()) {
            file.delete();
        }
        return imageMapper.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(ImageDO imageDO) {
        if (null != imageDO.getImgUrl() && !"".equals(imageDO.getImgUrl())) {
            return imageMapper.save(imageDO);
        }
        return 0;
    }

    public String upload(MultipartFile file) {
        return FileUploadUtil.uploadImg(file, filePath);
    }
}
