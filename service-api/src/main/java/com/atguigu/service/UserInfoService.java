package com.atguigu.service;

import entity.UserInfo;
import service.BaseService;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/5
 */
public interface UserInfoService extends BaseService<UserInfo> {

    UserInfo getUserInfoByPhone(String phone);
}
