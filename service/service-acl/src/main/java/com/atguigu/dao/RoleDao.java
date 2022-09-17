package com.atguigu.dao;

import dao.BaseDao;
import entity.Role;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/22 22:57
 */
public interface RoleDao extends BaseDao<Role> {
    List<Role> findAll();

}
