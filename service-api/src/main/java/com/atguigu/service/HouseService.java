package com.atguigu.service;

import com.github.pagehelper.PageInfo;
import entity.House;
import service.BaseService;
import vo.HouseQueryVo;
import vo.HouseVo;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/31 20:54
 */
public interface HouseService extends BaseService<House> {
    //发布或取消发布
    void publish(Long id, Integer status);

    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
