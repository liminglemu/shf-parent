package com.atguigu.service;

import entity.Role;
import service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/22 22:57
 */
public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

    //根据用户id查询用户的角色
    Map<String, Object> findRolesByAdminId(Long adminId);

    void assignRole(Long adminId, Long[] roleIds);
}
