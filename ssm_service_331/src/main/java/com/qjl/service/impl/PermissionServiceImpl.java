package com.qjl.service.impl;

import com.qjl.dao.PermissionDao;
import com.qjl.domain.Permission;
import com.qjl.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public List<Permission> findAllParentPermission() {
        return permissionDao.findAllParentPermission();
    }

    @Override
    public void save(Permission permission) {
        permissionDao.save(permission);
    }
}