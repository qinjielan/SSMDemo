package com.qjl.service.impl;

import com.qjl.dao.RoleDao;
import com.qjl.domain.Role;
import com.qjl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleDao roleDao;


    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findById(Integer roleId) {
        return roleDao.findById(roleId);
    }

    @Override
    public void addPermissionsToRole(Integer roleId, Integer[] ids) {
        //1. 删除所有的关系
        roleDao.delPermissionsFromRole(roleId);
        //2. 维护新的关系
        if (ids != null){
            for (Integer permissionId : ids) {
                roleDao.addPermissionToRole(roleId ,permissionId);
            }
        }
    }
}
