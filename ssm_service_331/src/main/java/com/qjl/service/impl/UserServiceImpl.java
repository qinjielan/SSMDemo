package com.qjl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qjl.dao.UserDao;
import com.qjl.domain.Role;
import com.qjl.domain.SysUser;
import com.qjl.service.UserService;
import com.qjl.utils.MD5Utils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userDao.findByUsername(username);
        if (sysUser != null) {
            //角色集合对象
            /*Collection<GrantedAuthority> authorities = new ArrayList<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            authorities.add(grantedAuthority);*/
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role : sysUser.getRoleList()) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+ role.getRoleName());
                authorities.add(grantedAuthority);
            }
            /**
             * 用户名
             * 密码
             * 角色列表
             */
            UserDetails user = new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
            return user;
        }
        return null;
    }

    @Override
    public List<SysUser> findAll() {
        return userDao.findAll();
    }

    @Override
    public PageInfo<SysUser> findByPageHelper(Integer currPage, Integer pageSize) {
        PageHelper.startPage(currPage, pageSize);
        List<SysUser> sysUserList = userDao.findAll();
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserList,3);
        return pageInfo;
    }

    @Override
    public void save(SysUser user) {
        //明文密码
        String password = user.getPassword();
        //加密
        String securityPassword = passwordEncoder.encode(password);
        //加密后的密码存储到user对象中
        user.setPassword(securityPassword);
        userDao.save(user);
    }

    @Override
    public boolean isUniqueUsername(String username) {
        SysUser user = userDao.findAllUsersByUsername(username);
        //如果user为null，说明可以使用此用户名，返回true
        //如果user不为null，说明不可以使用此用户名，返回false
        return user == null;
    }

    @Override
    public SysUser findById(Integer userId) {
        return userDao.findById(userId);
    }

    @Override
    public void addRolesToUser(Integer userId, Integer[] ids) {
        //先清空该用户拥有的所有的角色
        userDao.delRolesFromUser(userId);
        //维护新的关系
        //判断数组是否为空
        if(ids != null){
            for (Integer roleId : ids) {
                userDao.saveRoleToUser(userId,roleId);
            }
        }
    }
}
