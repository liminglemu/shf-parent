package com.atguigu.service;

import entity.Community;
import service.BaseService;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/29 23:09
 */
public interface CommunityService extends BaseService<Community> {
    public List<Community> findAll();
}
