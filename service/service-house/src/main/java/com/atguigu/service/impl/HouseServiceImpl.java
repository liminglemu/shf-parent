package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.service.HouseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.BaseDao;
import entity.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.impl.BaseServiceImpl;
import vo.HouseQueryVo;
import vo.HouseVo;

import java.io.Serializable;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/31 20:54
 */
@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long id, Integer status) {
        //创建一个house对象
        House house = new House();
        house.setId(id);
        house.setStatus(status);

        houseDao.update(house);
    }

    //前端分页显示
    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        PageHelper.startPage(pageNum, pageSize);
        //调用HouseDao中前端分页及待条件查询的方法
        Page<HouseVo> page = houseDao.findPageList(houseQueryVo);

        //遍历pageInfo
        for (HouseVo houseVo : page) {
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            //获取楼层
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            //获取朝向
            String directionName = dictDao.getNameById(houseVo.getDirectionId());

            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page, 5);
    }

    //从写getById是为了展示房源详细信息中房源户型，楼层等信息
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        //获取户型
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        //获取楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        //获取朝向
        String directionName = dictDao.getNameById(house.getDirectionId());
        //获取建筑结构
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        //获取装修情况
        String decorationName = dictDao.getNameById(house.getDecorationId());
        //获取房屋用途
        String houseUserName = dictDao.getNameById(house.getHouseUseId());

        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setDirectionName(directionName);
        house.setBuildStructureName(buildStructureName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUserName);
        return house;
    }
}
