package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageInfo;
import entity.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;
import vo.UserFollowVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/7
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    //关注房源
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpSession session) {
        //获取userInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //调用UserFollowService中关注房源的方法
        userFollowService.follow(houseId, userInfo.getId());
        return Result.ok();
    }

    //查询我的关注
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result myFollow(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");

        //分页查询方法
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum, pageSize, userInfo.getId());
        return Result.ok(pageInfo);
    }

    //取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id) {
        userFollowService.cancelFollow(id);
        return Result.ok();
    }

}
