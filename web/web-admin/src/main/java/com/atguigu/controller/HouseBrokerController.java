package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import entity.Admin;
import entity.HouseBroker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/2 22:09
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {
    private final static String PAGE_SUCCESS = "common/successPage";

    @Reference
    private AdminService adminService;

    @Reference
    private HouseBrokerService houseBrokerService;

    //去添加经纪人的页面
    @RequestMapping("/create")
    public String goCreatePage(@RequestParam("houseId") Long houseId, Map map) {
        //将房源id放到request域中
        map.put("houseId", houseId);

        //查询所有用户的方法
        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);

        return "houseBroker/create";
    }

    //保存经纪人
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker) {
        //根据经济人id查询经纪人信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.insert(houseBroker);
        return PAGE_SUCCESS;
    }

    //去修改经纪人页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        //调用houseBrokerService根据id查询经纪人的方法
        HouseBroker broker = houseBrokerService.getById(id);
        map.put("houseBroker", broker);
        //查询所有用户的方法
        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);
        return "houseBroker/edit";
    }

    //跟新经纪人
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker) {
        //根据经济人id查询经纪人信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.update(houseBroker);
        return PAGE_SUCCESS;
    }

    //删除经纪人
    @RequestMapping("delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("brokerId") Long brokerId) {
        houseBrokerService.delete(brokerId);
        return "redirect:/house/" + houseId;
    }

}
