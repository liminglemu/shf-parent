package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.DictService;
import entity.Dict;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;

import java.util.List;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/4 17:57
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    //根据编码获取子节点
    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode) {
        //调用DictService中根据编码获取子节点
        List<Dict> listByDictCode = dictService.getDictByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

    //根据父id查询所有的子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId) {
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }


}
