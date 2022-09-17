package com.atguigu.dao;

import dao.BaseDao;
import entity.HouseUser;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/2 0:08
 */
public interface HouseUserDao extends BaseDao<HouseUser> {
    //根据房源id查询改房源的房东
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
