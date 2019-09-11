package com.qjl.dao;

import com.qjl.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionDao {
    @Select("select * from sys_permission")
    List<Permission> findAll();

    @Select("select * from sys_permission where pid = 0")
    List<Permission> findAllParentPermission();

    @Insert("insert into sys_permission values(permission_seq.nextval,#{permissionName},#{url},#{pid})")
    void save(Permission permission);

    @Select("select p.*  from sys_role_permission rp, sys_permission p where rp.permissionid = p.id and roleId = #{roleId}")
    public List<Permission> findPermissionListByRoleId(Integer roleId);
}