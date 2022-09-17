package com.atguigu.dao;

import dao.BaseDao;
import entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/10
 */
public interface RolePermissionDao extends BaseDao<RolePermission> {
    List<Long> findPermissionIdsByRoleId(Long roleId);

    void addRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deletePermissionIdsByRoleIds(Long roleId);
}
