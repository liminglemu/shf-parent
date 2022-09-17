package com.atguigu.service;

import entity.HouseBroker;
import service.BaseService;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/2 0:17
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {
    //根据房源id查询房源的经纪人
    List<HouseBroker> getHouseBrokerByHouseID(Long houseId);
}
