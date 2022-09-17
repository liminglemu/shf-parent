package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;
import vo.HouseQueryVo;
import vo.HouseVo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/4 20:20
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private UserFollowService userFollowService;

    //分页及待条件查询的方法
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo) {
        //调用houseService前端分页及待条件查询的方法
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }

    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session) {
        //查询房子信息
        House house = houseService.getById(id);
        //查询小区信息
        Community community = communityService.getById(house.getCommunityId());
        //获取经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseID(id);
        //获取房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImageByHouseAndType(id, 1);
        HashMap<String, Object> hashMap = new HashMap<>();
        //将房源信息，小区信息，房源图片放到map中
        hashMap.put("house", house);
        hashMap.put("community", community);
        hashMap.put("houseBrokerList", houseBrokerList);
        hashMap.put("houseImage1List", houseImage1List);
        //设置默认没有有关注房源
        //hashMap.put("isFollow", false);

        //设置一个变量
        Boolean isFollowed = false;
        //从session中获取userInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (userInfo != null) {
            //使用userFollowService查询是否关注房源
            isFollowed = userFollowService.isFollowed(userInfo.getId(), id);
        }
        //将isFollow放进map
        hashMap.put("isFollow", isFollowed);

        return Result.ok(hashMap);
    }

}
