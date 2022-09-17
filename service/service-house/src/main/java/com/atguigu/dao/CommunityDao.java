package com.atguigu.dao;

import dao.BaseDao;
import entity.Community;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/29 23:04
 */
public interface CommunityDao extends BaseDao<Community> {


    List<Community> findAll();
}
