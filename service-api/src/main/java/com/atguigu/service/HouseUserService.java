package com.atguigu.service;

import entity.HouseUser;
import service.BaseService;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/2 0:21
 */
public interface HouseUserService extends BaseService<HouseUser> {
    //根据房源id查询改房源的房东
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
