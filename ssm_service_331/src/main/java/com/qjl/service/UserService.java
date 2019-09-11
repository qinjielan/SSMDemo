package com.qjl.service;

import com.github.pagehelper.PageInfo;
import com.qjl.domain.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<SysUser> findAll();

    PageInfo<SysUser> findByPageHelper(Integer currPage, Integer pageSize);

    void save(SysUser user);

    boolean isUniqueUsername(String username);

    SysUser findById(Integer userId);

    void addRolesToUser(Integer userId, Integer[] ids);
}
