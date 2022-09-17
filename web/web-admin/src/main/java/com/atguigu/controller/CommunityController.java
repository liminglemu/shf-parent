package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import controller.BaseController;
import entity.Community;
import entity.Dict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/29 23:11
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    private final static String PAGE_SUCCESS = "common/successPage";
    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    //分页待条件查询的方法
    @PreAuthorize("hasAuthority('community.show')")
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        PageInfo<Community> page = communityService.findPage(filters);
        map.put("page", page);

        //根据编码获取北京所有的区
        List<Dict> areaList = dictService.getDictByDictCode("beijing");
        map.put("areaList", areaList);
        return "community/index";
    }

    //去添加小区的页面
    @PreAuthorize("hasAuthority('community.create')")
    @RequestMapping("/create")
    public String goToPage(Map map) {
        //根据编码获取北京所有的区
        List<Dict> areaList = dictService.getDictByDictCode("beijing");
        map.put("areaList", areaList);
        return "community/create";
    }

    //保存小区
    @RequestMapping("/save")
    public String save(Community community) {
        //添加小区方法
        communityService.insert(community);
        //返回成功页面
        return PAGE_SUCCESS;
    }//保存小区

    @PreAuthorize("hasAuthority('community.edit')")
    @RequestMapping("/edit/{id}")
    public String goEditPage(Map map, @PathVariable("id") Long id) {
        Community community = communityService.getById(id);
        map.put("community", community);
        return "community/edit";
    }

    @RequestMapping("/update")
    public String update(Community community) {
        communityService.update(community);
        return PAGE_SUCCESS;
    }

    @PreAuthorize("hasAuthority('community.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        communityService.delete(id);
        return "redirect:/community";
    }

}
