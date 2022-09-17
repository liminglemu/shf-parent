package com.atguigu.dao;

import com.github.pagehelper.Page;
import dao.BaseDao;
import entity.UserFollow;
import org.apache.ibatis.annotations.Param;
import vo.UserFollowVo;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/5
 */
public interface UserFollowDao extends BaseDao<UserFollow> {

    Integer getCountByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);

    Page<UserFollowVo> findPageList(Long id);
}
