package com.qjl.dao;

import com.qjl.domain.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface RoleDao {

    @Select("select * from sys_role")
    List<Role> findAll();

    @Insert("insert into sys_role values(role_seq.nextval, #{roleName},#{roleDesc})")
    void save(Role role);

    @Select("select r.* from sys_user_role ur, sys_role r where r.id = ur.roleId and userId = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "permissionList", column = "id",javaType = List.class,
                    many = @Many(select = "com.qjl.dao.PermissionDao.findPermissionListByRoleId",fetchType = FetchType.LAZY))
    })
    public List<Role> findRoleListByUserId(Integer userId);

    @Select("select * from sys_role where id = #{roleId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "permissionList", column = "id",javaType = List.class,
                    many = @Many(select = "com.qjl.dao.PermissionDao.findPermissionListByRoleId",fetchType = FetchType.LAZY))
    })
    Role findById(Integer roleId);

    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    void delPermissionsFromRole(Integer roleId);

    @Insert("insert into sys_role_permission values(#{param2},#{param1})")
    void addPermissionToRole(Integer roleId, Integer permissionId);
}
