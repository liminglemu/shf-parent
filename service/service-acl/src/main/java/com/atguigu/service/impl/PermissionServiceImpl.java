package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.service.PermissionService;
import dao.BaseDao;
import entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import service.impl.BaseServiceImpl;
import util.PermissionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/10
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //获取所有的权限
        List<Permission> permissionList = permissionDao.findAll();
        //根据角色id查询已分配的权限id
        List<Long> permissionIds = rolePermissionDao.findPermissionIdsByRoleId(roleId);
        //创建list
        List<Map<String, Object>> returnList = new ArrayList<>();

        //遍历所有的权限
        for (Permission permission : permissionList) {
            //判断当前权限的id在不在permissionList里面
            //map格式：{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
            //创建一个map
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());
            if (permissionIds.contains(permission.getId())) {
                //证明该权限已分配
                map.put("checked", true);
            }
            //将map放进List中
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        //调用permissionDao中deletePermissionIdsByRoleIds,根据roleId逻辑删除已分配的权限
        rolePermissionDao.deletePermissionIdsByRoleIds(roleId);
        for (Long permissionId : permissionIds) {
            //调用permissionDao中保存权限id 的方法
            if (permissionId != null) {
                rolePermissionDao.addRoleIdAndPermissionId(roleId, permissionId);
            }
        }
    }

    //根据userId查询用户的权限列表
    @Override
    public List<Permission> getMenuPermissionByAdminId(Long userId) {
        //判断是否为系统管理员
        List<Permission> permissionList = null;
        if (userId == 1) {
            //证明是系统管理员,获取所有的权限
            permissionList = permissionDao.findAll();
        } else {
            //根据用户id查询所有权限菜单
            permissionList = permissionDao.getMenuPermissionByAdminId(userId);
        }
        //通过permissionHelper工具类转换成树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);

        return treeList;
    }

    @Override
    public List<Permission> findAllMenu() {
        List<Permission> permissionList = permissionDao.findAll();
        if (CollectionUtils.isEmpty(permissionList)) {
            return null;
        }
        return PermissionHelper.bulid(permissionList);
    }


    @Override
    public List<String> getPermissionCodesByAdminId(Long id) {
        List<String> permissionCodes = null;
        //判断
        if (id == 1) {
            //证明系统管理员
            permissionCodes = permissionDao.getAllPermissionCodes();
        } else {
            //根据用户id查询权限
            permissionCodes = permissionDao.getPermissionCodesByAdminId(id);
        }
        return permissionCodes;
    }
}
