package com.atguigu.service;

import entity.Permission;
import service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/10
 */
public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    void assignPermission(Long roleId, Long[] permissionIds);

    List<Permission> getMenuPermissionByAdminId(Long userId);

    List<Permission> findAllMenu();

    List<String> getPermissionCodesByAdminId(Long id);
}
