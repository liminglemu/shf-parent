package com.atguigu.dao;

import entity.Dict;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/28 18:11
 */
public interface DictDao {
    List<Dict> findListByParentId(Long id);

    Integer isParentNode(Long id);

    //根据编码获取Dict对象
    Dict getDictByDictCode(String dictCode);

    //根据id获取name
    String getNameById(Long id);
}
