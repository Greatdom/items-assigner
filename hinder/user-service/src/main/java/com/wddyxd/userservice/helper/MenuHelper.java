package com.wddyxd.userservice.helper;


import com.alibaba.fastjson.JSONObject;
import com.wddyxd.userservice.pojo.Permissions;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: items-assigner
 * @description: 前端动态路由构建器
 * @author: wddyxd
 * @create: 2025-10-20 21:02
 **/

public class MenuHelper {


    public static List<JSONObject> build(List<Permissions> treeNodes) {
        List<JSONObject> menus = new ArrayList<>();
        if (treeNodes.size() == 1) {
            Permissions topNode = treeNodes.get(0);
            // 处理一级菜单
            for (Permissions one : topNode.getChildren()) {
                JSONObject oneMenu = createMenuJson(one, false);
                List<JSONObject> children = new ArrayList<>();

                // 处理二级菜单
                for (Permissions two : one.getChildren()) {
                    JSONObject twoMenu = createMenuJson(two, false);
                    children.add(twoMenu);

                    // 处理三级菜单
                    for (Permissions three : two.getChildren()) {
                        if (StringUtils.isEmpty(three.getPath())) continue;
                        JSONObject threeMenu = createMenuJson(three, true);
                        children.add(threeMenu);
                    }
                }

                oneMenu.put("children", children);
                menus.add(oneMenu);
            }
        }
        return menus;
    }

    /**
     * 创建菜单JSON对象的通用方法
     */
    private static JSONObject createMenuJson(Permissions permission, boolean hidden) {
        JSONObject menu = new JSONObject();
        menu.put("path", permission.getPath());
        menu.put("component", permission.getComponent());
        menu.put("name", "name_" + permission.getId());
        menu.put("hidden", hidden);

        // 处理元数据
        JSONObject meta = new JSONObject();
        meta.put("title", permission.getName());
        meta.put("icon", permission.getIcon());
        menu.put("meta", meta);

        // 一级菜单需要redirect属性
        if (!hidden && (permission.getPid() == null|| permission.getPid() == 1L)) {
            menu.put("redirect", "noredirect");
        }

        return menu;
    }
}
