package com.yzm.majorserver.web.dataobject;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:04 2019/6/14
 * ===========================
 */
@Data
public class ImageDO {

    private Integer id;

    @NotBlank(message = "图片路径不能为空")
    private String imgUrl;

    private String description;

    private Integer type;
}
