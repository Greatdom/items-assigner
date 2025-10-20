package com.wddyxd.userservice.helper;


import com.wddyxd.userservice.pojo.Permissions;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: items-assigner
 * @description: 权限数据构建器
 * @author: wddyxd
 * @create: 2025-10-20 21:03
 **/

public class PermissionsHelper {


    public static List<Permissions> build(List<Permissions> treeNodes) {
        List<Permissions> trees = new ArrayList<>();
        for (Permissions treeNode : treeNodes) {
            if (treeNode.getPid()==0L) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
                break;
            }
        }
        return trees;
    }


    public static Permissions findChildren(Permissions treeNode,List<Permissions> treeNodes) {
        treeNode.setChildren(new ArrayList<Permissions>());

        for (Permissions it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
