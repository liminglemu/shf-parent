package com.atguigu.dao;

import com.github.pagehelper.Page;
import dao.BaseDao;
import entity.House;
import vo.HouseQueryVo;
import vo.HouseVo;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/31 20:55
 */
public interface HouseDao extends BaseDao<House> {
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
