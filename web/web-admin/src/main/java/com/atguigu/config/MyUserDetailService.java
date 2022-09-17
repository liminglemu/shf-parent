package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/12
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    //用户登录时springSecurity调用此方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByUserName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        //查询到进行用户授权
        //调用permissionService获取当前用户权限码
        List<String> permissionCodes = permissionService.getPermissionCodesByAdminId(admin.getId());
        //遍历得到每一个权限码
        //创建一个用于授权的集合
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (String permissionCode : permissionCodes) {
            //创建GrantedAuthority对象
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
            //将simpleGrantedAuthority放到grantedAuthorityList集合中
            grantedAuthorityList.add(simpleGrantedAuthority);
        }

        /*
        * 给用户授权有两种方式
        * 1.通过角色的方式表示。Role_ADMIN
        * 2.直接设置权限。Delete、Query、Update
        * */

        //return new User(username, admin.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        //真正的进行授权
        return new User(username, admin.getPassword(), grantedAuthorityList);
    }
}
