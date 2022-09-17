package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.DictDao;
import com.atguigu.service.DictService;
import entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/28 18:10
 */
@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //根据父id查询该节点下所有的子节点
        List<Dict> dictList = findListByParentId(id);

        //创建返回的list
        //[{ id:'0331',name:'n3.3.n1',	isParent:true}]
        List<Map<String, Object>> zNode = new ArrayList<>();

        //遍历dictList
        for (Dict dict : dictList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("name", dict.getName());
            //根据该节点是否为父节点
            Integer count = isParentNode(dict.getId());
            map.put("isParent", count > 0 ? true : false);

            zNode.add(map);
        }

        return zNode;
    }

    @Override
    public List<Dict> findListByParentId(Long parentid) {
        return dictDao.findListByParentId(parentid);
    }

    @Override
    public Integer isParentNode(Long id) {
        return dictDao.isParentNode(id);
    }

    @Override
    public List<Dict> getDictByDictCode(String dictCode) {
        Dict dictByDictCode = dictDao.getDictByDictCode(dictCode);
        if (dictByDictCode == null) {
            return null;
        }
        return this.findListByParentId(dictByDictCode.getId());
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }
}
