package com.yzm.majorserver.web.mapper;

import com.yzm.majorserver.web.dataobject.ImageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:03 2019/6/14
 * ===========================
 */
@Mapper
@Repository
public interface ImageMapper {

    /**
     * 通过id查询
     *
     * @param id id
     * @return
     */
    ImageDO findById(@Param("id") Integer id);

    /**
     * 上传图片
     *
     * @param imageDO
     * @return
     */
    Integer save(ImageDO imageDO);

    /**
     * 修改图片
     *
     * @param imageDO
     * @return
     */
    Integer update(ImageDO imageDO);

    /**
     * 删除图片
     *
     * @param id
     * @return
     */
    Integer delete(@Param("id") Integer id);

    /**
     * 查询
     *
     * @param type
     * @return
     */
    List<ImageDO> list(@Param("type") Integer type);
}
