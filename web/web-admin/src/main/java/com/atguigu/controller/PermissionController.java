package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.PermissionService;
import controller.BaseController;
import entity.Permission;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/11
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    private final static String PAGE_SUCCESS = "common/successPage";

    @Reference
    private PermissionService permissionService;

    //去菜单管理
    @PreAuthorize("hasAuthority('permission.show')")
    @RequestMapping
    public String permission(ModelMap modelMap) {
        List<Permission> list = permissionService.findAllMenu();
        modelMap.addAttribute("list", list);
        return "permission/index";
    }

    //添加
    @PreAuthorize("hasAuthority('permission.create')")
    @RequestMapping("/create")
    public String goInsetPage(ModelMap modelMap, Permission permission) {
        modelMap.addAttribute("permission", permission);
        return "permission/create";
    }

    //保存
    @RequestMapping("/save")
    public String insert(Permission permission) {
        permissionService.insert(permission);
        return PAGE_SUCCESS;
    }

    //去修改
    @PreAuthorize("hasAuthority('permission.edit')")
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        Permission permission = permissionService.getById(id);
        modelMap.addAttribute("permission", permission);
        return "permission/edit";
    }

    //修改保存
    @RequestMapping("/update")
    public String update(Permission permission) {
        permissionService.update(permission);
        return PAGE_SUCCESS;
    }

    //删除
    @PreAuthorize("hasAuthority('permission.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        permissionService.delete(id);
        return "redirect:/permission";
    }
}
