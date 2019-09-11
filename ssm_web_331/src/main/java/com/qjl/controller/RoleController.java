package com.qjl.controller;


import com.qjl.domain.Permission;
import com.qjl.domain.Role;
import com.qjl.service.PermissionService;
import com.qjl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @Resource
    PermissionService permissionService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(){
        List<Role> roleList = roleService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("role-list");
        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(Role role){
        roleService.save(role);
        return "redirect:/role/findAll";
    }

    /**
     * 添加权限到角色的数据回显
     * @param roleId
     * @return
     */
    @RequestMapping("/addPermissionsToRoleUI")
    public ModelAndView addPermissionsToRoleUI(Integer roleId){
        //查询数据
        //1. 所有的权限
        List<Permission> permissionList = permissionService.findAll();
        //2. 查询已经拥有的权限
        Role role = roleService.findById(roleId);
        //把已经拥有的权限的id拼接为一个字符串
        StringBuffer  sb = new StringBuffer();
        for (Permission permission : role.getPermissionList()) {
            sb.append(",");
            sb.append(permission.getId());
            sb.append(",");
        }
        //创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        //添加数据
        modelAndView.addObject("permissionList", permissionList);
        modelAndView.addObject("str", sb.toString());
        //角色的id：保存权限时使用
        modelAndView.addObject("roleId", role.getId());
        //指定页面
        modelAndView.setViewName("role-permission-add");
        return modelAndView;
    }

    /**
     *  添加权限到角色
     * @param roleId  角色id
     * @param ids  角色对应的权限的id
     * @return
     */
    @RequestMapping("/addPermissionsToRole")
    public String addPermissionsToRole(Integer roleId, Integer[] ids){
        //保存操作
        roleService.addPermissionsToRole(roleId, ids);
        //请求查询全部
        return "redirect:/role/findAll";
    }
}
