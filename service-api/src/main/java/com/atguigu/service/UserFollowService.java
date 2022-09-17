package com.atguigu.service;

import com.github.pagehelper.PageInfo;
import entity.UserFollow;
import service.BaseService;
import vo.UserFollowVo;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/5
 */
public interface UserFollowService extends BaseService<UserFollow> {
    //关注房源
    void follow(Long houseId, Long id);

    Boolean isFollowed(Long userId, Long houseId);

    //我的关注
    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long id);

    //取消关注
    void cancelFollow(Long id);
}
