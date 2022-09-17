package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.service.DictService;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.BaseDao;
import entity.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.impl.BaseServiceImpl;
import vo.UserFollowVo;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/5
 */
@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Reference
    private DictService dictService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long houseId, Long id) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(id);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        Integer conut = userFollowDao.getCountByUserIdAndHouseId(userId, houseId);
        return conut > 0;
    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long id) {
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowDao.findPageList(id);
        //遍历page
        for (UserFollowVo userFollowVo : page) {
            //房屋类型
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            //获取楼层
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            //获取朝向
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(houseTypeName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page, 5);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }
}
