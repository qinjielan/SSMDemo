package com.qjl.service;

import com.qjl.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();

    List<Permission> findAllParentPermission();

    void save(Permission permission);
}