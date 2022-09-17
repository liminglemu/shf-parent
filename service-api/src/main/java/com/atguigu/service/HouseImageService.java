package com.atguigu.service;

import entity.HouseImage;
import org.apache.ibatis.annotations.Param;
import service.BaseService;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/2 0:19
 */
public interface HouseImageService extends BaseService<HouseImage> {
    //根据房源id和类型查询房源或房产图片
    List<HouseImage> getHouseImageByHouseAndType(Long houseId, Integer type);
}
