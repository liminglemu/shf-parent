package com.atguigu.dao;

import dao.BaseDao;
import entity.Permission;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/10
 */
public interface PermissionDao extends BaseDao<Permission> {

    List<Permission> findAll();

    List<Permission> getMenuPermissionByAdminId(Long userId);

    List<String> getAllPermissionCodes();

    List<String> getPermissionCodesByAdminId(Long id);
}
