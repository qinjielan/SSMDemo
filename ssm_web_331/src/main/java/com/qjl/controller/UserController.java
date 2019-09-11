package com.qjl.controller;

import com.github.pagehelper.PageInfo;
import com.qjl.dao.UserDao;
import com.qjl.domain.Role;
import com.qjl.domain.SysUser;
import com.qjl.service.RoleService;
import com.qjl.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
@Secured({"ROLE_ADMIN"})
public class UserController {
    @Resource
    UserService userService;
    @Resource
    RoleService roleService;

   /* @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(value = "currPage", required = false, defaultValue = "1") Integer currPage,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        PageInfo<SysUser> pageInfo = userService.findByPageHelper(currPage, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.setViewName("user-list");
        return modelAndView;
    }*/
   @RequestMapping("/findAll")
   public ModelAndView findAll(){
       List<SysUser> userList = userService.findAll();
       ModelAndView modelAndView = new ModelAndView();
       modelAndView.addObject("userList",userList);
       modelAndView.setViewName("user-list");
       return modelAndView;
   }

    @RequestMapping("/save")
    public String save(SysUser user){
        userService.save(user);
        return "redirect:/user/findAll";
    }

    @RequestMapping("/isUniqueUsername")
    @ResponseBody
    public String isUniqueUsername(String username){
        //判断是否存在该用户名
        boolean b = userService.isUniqueUsername(username);

        return String.valueOf(b);
    }

    @RequestMapping("/details")
    public ModelAndView details(Integer userId){
        SysUser user = userService.findById(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("user-show");
        return modelAndView;
    }

    /**
     * 用户添加角色的数据回显
     * @param userId
     * @return
     */
    @RequestMapping("/addRoleToUserUI")
    public ModelAndView addRoleToUserUI(Integer userId){
        //所有的角色
        List<Role> roleList = roleService.findAll();
        //当前用户拥有的角色
        SysUser user = userService.findById(userId);
        List<Role> userWithRoleList = user.getRoleList();
        //把该用户拥有的角色的id拼接一个字符串
        // str = ,1,,2,,3,,4,,5,,6,,12,    ,1,
        StringBuffer sb = new StringBuffer();
        for (Role role : userWithRoleList) {
            sb.append(",");
            sb.append(role.getId());
            sb.append(",");
        }
        ModelAndView modelAndView = new ModelAndView();
        //添加所有的角色列表
        modelAndView.addObject("roleList",roleList);
        //已经拥有的角色列表
        modelAndView.addObject("str",sb.toString());
        //查询是哪个用户的角色
        modelAndView.addObject("userId", user.getId() );
        modelAndView.setViewName("user-role-add");
        return modelAndView;
    }

    /**
     *
     * @param userId  要给哪个用户添加角色
     * @param ids  要给用户添加的角色的id的数组
     * @return
     */
    @RequestMapping("/addRolesToUser")
    public String addRolesToUser(Integer userId, Integer[] ids){
        //保存操作
        userService.addRolesToUser(userId ,ids);
        //请求查询全部
        return "redirect:/user/findAll";

    }
}
