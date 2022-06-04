package com.hl.springboot.utils;

import com.hl.springboot.entity.Menu;

import java.util.*;

/**
 * 菜单树形结构工具类
 */
public class TreeUtils {

    public static List<Menu> buildTree(List<Menu> list, Integer pid, List<String> roleList) {
        List<Menu> tree = new ArrayList<>();
        for (Menu menu : list) {
            if (Objects.equals(menu.getPid(), pid)) {
                tree.add(findChild(menu, list, roleList));
            }
        }
        return tree;
    }

    public static Menu findChild(Menu menu, List<Menu> list, List<String> roleList) {
        for (Menu m : list) {
            if (Objects.equals(m.getPid(), menu.getId())) {
                if (menu.getChildren() == null) {
                    menu.setChildren(new ArrayList<Menu>());
                    menu.setRoles(roleList);
                }
                menu.getChildren().add(findChild(m, list, roleList));
            } else {
                m.setRoles(roleList);
            }
        }
        return menu;
    }
}
