package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import entity.Admin;
import entity.Permission;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/23 14:46
 */
@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    /*@GetMapping("/")
    public String index() {

        return "frame/index";
    }*/

    //去首页
    @RequestMapping("/")
    public String index(Map map) {
        //设置默认的用户id
        //Long userId = 1L;
        //Admin admin = adminService.getById(userId);

        //通过springSecurity获取User对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //调用 AdminService根据用户名获取Admin对象的方法
        Admin admin = adminService.getAdminByUserName(user.getUsername());

        map.put("admin", admin);
        //用户的id查询用户菜单权限的方法
        List<Permission> permissionList = permissionService.getMenuPermissionByAdminId(admin.getId());
        map.put("permissionList", permissionList);
        return "frame/index";
    }

    //去主页面
    @GetMapping("/main")
    public String main() {
        return "frame/main";
    }

    //去spring的登录页面的方法
    @RequestMapping("/login")
    public String login() {
        return "frame/login";
    }

    //登出
    /*@RequestMapping("/logout")
    public String logout(HttpSession session) {
        //让session失效
        session.invalidate();
        //重定向到登录页面
        return "redirect:/login";
    }*/

    @RequestMapping("/auth")
    public String auth() {
        return "frame/auth";
    }

}
