package com.atguigu.dao;

import dao.BaseDao;
import entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/10
 */
public interface AdminRoleDao extends BaseDao<AdminRole> {

    List<Long> findRoleIdsByAdminId(Long adminId);

    void deleteRoleIdsByAdminId(Long adminId);

    void addRoleAndAdminId(@Param("roleId") Long roleId, @Param("adminId") Long adminId);
}
