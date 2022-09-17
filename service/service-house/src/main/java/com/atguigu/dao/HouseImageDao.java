package com.atguigu.dao;

import dao.BaseDao;
import entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/2 0:00
 */
public interface HouseImageDao extends BaseDao<HouseImage> {
    //根据房源id和类型查询房源或房产图片
    List<HouseImage> getHouseImageByHouseAndType(@Param("houseId") Long houseId, @Param("type") Integer type);
}
