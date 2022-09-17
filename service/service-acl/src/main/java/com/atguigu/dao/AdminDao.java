package com.atguigu.dao;

import dao.BaseDao;
import entity.Admin;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/23 23:13
 */
public interface AdminDao extends BaseDao<Admin> {
    List<Admin> findAll();

    Admin getAdminByUserName(String username);
}
