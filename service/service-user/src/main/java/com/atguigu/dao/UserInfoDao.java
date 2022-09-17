package com.atguigu.dao;

import dao.BaseDao;
import entity.UserInfo;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/5
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);
}
