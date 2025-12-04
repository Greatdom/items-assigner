package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.VO.RoleVO;
import com.wddyxd.userservice.pojo.entity.Role;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-09 17:15
 **/

public interface IRoleService extends IService<Role> {

    public Page<Role> List(SearchDTO searchDTO) ;

    public RoleVO detail(Long id);

    public void assign(Long userId, Long[] roleIds);

    public void add(String name);

    public void update(String name);

    public void delete(@PathVariable Long id);

}
