package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.service.CommunityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.BaseDao;
import entity.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.impl.BaseServiceImpl;
import util.CastUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/29 23:09
 */
@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    //重写分页的方法，目的是给小区中的区域和板块的名字赋值
    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);

        //调用communityDao中分页及待条件的查询方法
        Page<Community> page = communityDao.findPage(filters);
        for (Community community : page) {
            //根据区域的id获取区域的名字
            String areaName = dictDao.getNameById(community.getAreaId());
            //根据板块的id获取板块的名字
            String palteName = dictDao.getNameById(community.getPlateId());
            //给community区域和板块名赋值
            community.setAreaName(areaName);
            community.setPlateName(palteName);
        }
        return new PageInfo<>(page, 10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }


    @Override
    public Community getById(Serializable id) {

        Community community = communityDao.getById(id);

        //根据区域的id获取区域的名字
        String areaName = dictDao.getNameById(community.getAreaId());
        //根据板块的id获取板块的名字
        String palteName = dictDao.getNameById(community.getPlateId());
        //给community区域和板块名赋值
        community.setAreaName(areaName);
        community.setPlateName(palteName);

        return community;
    }
}
