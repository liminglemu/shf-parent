package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import controller.BaseController;
import entity.*;
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
 * @date 2022/8/31 20:32
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    private final static String PAGE_SUCCESS = "common/successPage";

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseUserService houseUserService;

    @Reference
    private HouseBrokerService houseBrokerService;

    //分页及待条件查询的方法
    @PreAuthorize("hasAuthority('house.show')")
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        PageInfo<House> page = houseService.findPage(filters);
        map.put("page", page);

        houseList(map);

        return "house/index";
    }

    @PreAuthorize("hasAuthority('house.create')")
    @RequestMapping("/create")
    public String goAddPage(Map map) {
        houseList(map);
        return "house/create";
    }

    @RequestMapping("/save")
    public String save(House house) {
        houseService.insert(house);
        return PAGE_SUCCESS;
    }

    //去修改的页面
    @PreAuthorize("hasAuthority('house.edit')")
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        //查询
        House byId = houseService.getById(id);
        map.put("house", byId);
        houseList(map);
        return "house/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(House house) {
        houseService.update(house);
        return PAGE_SUCCESS;
    }

    //删除
    @PreAuthorize("hasAuthority('house.delete')")
    @RequestMapping("/delete/{id}")
    public String deleted(@PathVariable("id") Long id) {
        houseService.delete(id);
        return "redirect:/house";
    }

    //发布和取消发布
    @PreAuthorize("hasAuthority('house.publish')")
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id, @PathVariable("status") Integer status, Map map) {
        houseService.publish(id, status);
        houseList(map);
        //重定向
        return "redirect:/house";
    }

    //查看详情
    @RequestMapping("/{id}")
    public String show(@PathVariable("id") Long id, Map map) {
        //根据id查询房源信息
        House house = houseService.getById(id);
        map.put("house", house);
        //调用community根据小区id查询小区方法
        Community community = communityService.getById(house.getCommunityId());
        map.put("community", community);

        //查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImageByHouseAndType(id, 1);
        map.put("houseImage1List", houseImage1List);
        //查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getHouseImageByHouseAndType(id, 2);
        map.put("houseImage2List", houseImage2List);
        //查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseID(id);
        map.put("houseBrokerList", houseBrokerList);
        //查询房东
        List<HouseUser> houseUserList = houseUserService.getHouseUserByHouseId(id);
        map.put("houseUserList", houseUserList);
        return "house/show";
    }

    private void houseList(Map map) {
        //获取所有的小区
        List<Community> communityList = communityService.findAll();
        //获取所有户型
        List<Dict> houseTypeList = dictService.getDictByDictCode("houseType");
        //获取楼层
        List<Dict> floorList = dictService.getDictByDictCode("floor");
        //获取建筑结构
        List<Dict> buildStructureList = dictService.getDictByDictCode("buildStructure");
        //获取朝向
        List<Dict> directionList = dictService.getDictByDictCode("direction");
        //获取装修情况
        List<Dict> decorationList = dictService.getDictByDictCode("decoration");
        //获取房屋用途
        List<Dict> houseUseList = dictService.getDictByDictCode("houseUse");
        //将以上信息放到request中
        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);
    }


}
