package com.atguigu.service;

import entity.Admin;
import service.BaseService;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/23 23:13
 */
public interface AdminService extends BaseService<Admin> {

    List<Admin> findAll();

    Admin getAdminByUserName(String username);
}
