package com.yzm.majorserver.common.utils;

import com.yzm.majorserver.web.dataobject.MenuDO;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:15 2019/6/18
 * ===========================
 */
public class MenuUtil {
    public static List<MenuDO> getMenus(Integer id, List<MenuDO> menuDOS) {
        List<MenuDO> menus = new ArrayList<>();
        menuDOS.forEach(menuDO -> {
            if (menuDO.getParentId().equals(id)) {
                menus.add(menuDO);
            }
        });
        for (MenuDO menuDTO : menus) {
            menuDTO.setChildren(getMenus(menuDTO.getId(), menuDOS));
        }
        if (menus.size() == 0) {
            return menus;
        }
        return menus;
    }
}
