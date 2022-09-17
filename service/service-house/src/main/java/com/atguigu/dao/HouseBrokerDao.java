package com.atguigu.dao;

import dao.BaseDao;
import entity.HouseBroker;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/1 22:31
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    //根据房源id查询房源经纪人
    List<HouseBroker> getHouseBrokerByHouseID(Long houseId);

}
