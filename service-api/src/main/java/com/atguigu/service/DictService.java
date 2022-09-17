package com.atguigu.service;

import entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/28 18:10
 */
public interface DictService {

    //查询根据id查询数据字典的数据，通过zTree进行渲染
    List<Map<String, Object>> findZnodes(Long id);

    //根据编码获取该节点下的所有子节点
    List<Dict> findListByParentId(Long parentid);

    Integer isParentNode (Long id);

    //根据编码获取改节点下所有节点的字节点
    List<Dict> getDictByDictCode(String dictCode);

    String getNameById(Long id);
}