package com.yzm.majorserver.web.dataobject;

import lombok.Data;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:19 2019/6/18
 * ===========================
 */
@Data
public class MenuDO {

    private Integer id;

    private String name;

    private String path;

    private Integer parentId;

    private List<MenuDO> children;

}
