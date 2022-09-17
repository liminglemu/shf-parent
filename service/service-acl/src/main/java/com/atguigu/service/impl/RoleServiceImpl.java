package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.service.RoleService;
import dao.BaseDao;
import entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/22 22:57
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRolesByAdminId(Long adminId) {
        //获取所有的角色
        List<Role> roleList = roleDao.findAll();
        //获取用户已选择的角色的角色id
        List<Long> releIds = adminRoleDao.findRoleIdsByAdminId(adminId);

        //创建两个list,一个是未选中的角色，一个是已选中的角色
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        //遍历所有的角色
        for (Role role : roleList) {
            //判断当前角色id在不在roleIds中
            if (releIds.contains(role.getId())) {
                //将当前角色放到list中
                assginRoleList.add(role);
            } else {
                //否则就是未选择的角色
                noAssginRoleList.add(role);
            }
        }
        //创建返回的map
        Map<String, Object> map = new HashMap<>();
        map.put("noAssginRoleList", noAssginRoleList);
        map.put("assginRoleList", assginRoleList);

        return map;
    }

    //分配角色
    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        //根据用户id将已分配的角色删除
        adminRoleDao.deleteRoleIdsByAdminId(adminId);
        //遍历所有角色的id
        for (Long roleId : roleIds) {
            if (roleId != null) {
                //根据角色id和用户id插入到数据库中
                adminRoleDao.addRoleAndAdminId(roleId, adminId);
            }
        }
    }

    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }

}
