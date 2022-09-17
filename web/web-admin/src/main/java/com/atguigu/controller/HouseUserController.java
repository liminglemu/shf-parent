package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.HouseUserService;
import entity.HouseUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/2 23:35
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController {
    private final static String PAGE_SUCCESS = "common/successPage";

    @Reference
    private HouseUserService houseUserService;

    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map) {
        map.put("houseId", houseId);
        return "houseUser/create";
    }

    @RequestMapping("/save")
    public String insert(HouseUser houseUser) {
        houseUserService.insert(houseUser);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        HouseUser houseUser = houseUserService.getById(id);
        map.put("houseUser", houseUser);
        return "houseUser/edit";
    }

    @RequestMapping("/update")
    public String update(HouseUser houseUser) {
        houseUserService.update(houseUser);
        return PAGE_SUCCESS;
    }

    @RequestMapping("delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        houseUserService.delete(id);
        return "redirect:/house/" + houseId;
    }

}
