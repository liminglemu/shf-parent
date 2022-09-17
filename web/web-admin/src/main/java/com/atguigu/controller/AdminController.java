package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import controller.BaseController;
import entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import util.QiniuUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/23 23:12
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private final static String PAGE_SUCCESS = "common/successPage";
    @Reference
    AdminService adminService;

    @Reference
    RoleService roleService;

    //注入密码加密器
    @Autowired
    private PasswordEncoder getPassWordEncoder;

    //分页及待条件的查询
    @PreAuthorize("hasAuthority('admin.show')")
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);

        map.put("filters", filters);
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        map.put("page", pageInfo);
        return "admin/index";
    }

    @PreAuthorize("hasAuthority('admin.create')")
    @RequestMapping("/create")
    public String gotToCreatePage() {
        return "admin/create";
    }

    @RequestMapping("/save")
    public String insert(Admin admin) {
        //对admin中的密码进行加密
        admin.setPassword(getPassWordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);
        return PAGE_SUCCESS;
    }

    @PreAuthorize("hasAuthority('admin.edit')")
    @RequestMapping("/edit/{id}")
    public String goToEditPage(@PathVariable("id") Long id, Map map) {
        Admin admin = adminService.getById(id);
        map.put("admin", admin);
        return "admin/edit";
    }

    @RequestMapping("/update")
    public String update(Admin admin) {
        adminService.update(admin);
        return PAGE_SUCCESS;
    }

    @PreAuthorize("hasAuthority('admin.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return "redirect:/admin";
    }

    //上传头像
    @RequestMapping("/uploadShow/{id}")
    public String goUploadPage(Map map, @PathVariable("id") Long id) {
        //将用户id放到request中
        map.put("id", id);
        return "admin/upload";
    }

    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        try {
            //根据用户id调用adminservice方法查询用户的方法
            Admin admin = adminService.getById(id);
            //获取字节数组
            byte[] bytes = file.getBytes();
            //通过七牛工具类上传
            String fileName = UUID.randomUUID().toString();
            QiniuUtils.upload2Qiniu(bytes, fileName);
            //设置用户的头像地址
            admin.setHeadUrl("http://rhmxcwpqq.hn-bkt.clouddn.com/" + fileName);
            //调用adminService中更新的方法
            adminService.update(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PAGE_SUCCESS;
    }

    //去分配角色页面
    @PreAuthorize("hasAuthority('admin.assgin')")
    @RequestMapping("/assignShow/{adminId}")
    public String goAssignShowPage(@PathVariable("adminId") Long adminId, ModelMap modelMap) {
        //将用户id放进request域中
        modelMap.addAttribute("adminId", adminId);

        Map rolesByAdminId = roleService.findRolesByAdminId(adminId);
        modelMap.addAllAttributes(rolesByAdminId);
        return "admin/assignShow";
    }

    //分配角色
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds) {
        //调用rolesService分配角色的方法
        roleService.assignRole(adminId, roleIds);
        return PAGE_SUCCESS;
    }

}
