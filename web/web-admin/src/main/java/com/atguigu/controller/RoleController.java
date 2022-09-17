package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import controller.BaseController;
import entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/22 22:56
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    public static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    //分页及条件查询
    @PreAuthorize("hasAuthority('role.show')")
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        //调用roleService分页及待条件查询的方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        map.put("page", pageInfo);
        return "role/index";
    }

    //跳转添加页面
    @PreAuthorize("hasAuthority('role.create')")
    @GetMapping("/create")
    public String goAddPage() {
        return "role/create";
    }

    @PostMapping("/save")
    public String save(Role role) {
        roleService.insert(role);
        //重定向
        /*return "redirect:/role";*/
        //去common下的success页面
        return SUCCESS_PAGE;
    }

    @PreAuthorize("hasAuthority('role.delete')")//此时只有delete权限的时候才能调用
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        //删除没有弹窗，是在当前页面删除，所以使用重定向
        return "redirect:/role";
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Integer id, Map map) {
        //先查询出要修改的对象
        Role role = roleService.getById(id);
        //将查询到的角色放到request中
        map.put("role", role);
        //去修改的页面
        return "role/edit";
    }

    @PostMapping("/update")
    public String update(Role role) {
        roleService.update(role);
        return SUCCESS_PAGE;
    }

    //去分配权限
    @PreAuthorize("hasAuthority('role.assgin')")
    @RequestMapping("/assignShow/{roleId}")
    public String goAssignShowPage(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        modelMap.addAttribute("roleId", roleId);
        //使用premissionService根据角色id获取权限的方法
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        //将zNodes放到request中
        modelMap.addAttribute("zNodes", zNodes);
        return "role/assignShow";
    }

    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId, @RequestParam("permissionIds") Long[] permissionIds) {
        //调用permissionService保存权限
        permissionService.assignPermission(roleId, permissionIds);
        return SUCCESS_PAGE;
    }


}
