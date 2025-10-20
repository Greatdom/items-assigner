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


    public static List<JSONObject> bulid(List<Permissions> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            Permissions topNode = treeNodes.getFirst();
            //左侧一级菜单
            List<Permissions> oneMeunList = topNode.getChildren();
            for(Permissions one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_"+one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<Permissions> twoMeunList = one.getChildren();
                for(Permissions two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_"+two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<Permissions> threeMeunList = two.getChildren();
                    for(Permissions three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getPath())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);

                        List<Permissions> fourMeunList = three.getChildren();
                        for(Permissions four :fourMeunList) {
                            if(StringUtils.isEmpty(four.getPath())) continue;

                            JSONObject fourMeun = new JSONObject();
                            fourMeun.put("path", four.getPath());
                            fourMeun.put("component", four.getComponent());
                            fourMeun.put("name", "name_"+four.getId());
                            fourMeun.put("hidden", true);

                            JSONObject fourMeta = new JSONObject();
                            fourMeta.put("title", four.getName());
                            fourMeun.put("meta", fourMeta);

                            children.add(fourMeun);
                        }
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }
}
