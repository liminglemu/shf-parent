package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.DictService;
import entity.Dict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/8/28 18:05
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    //展示数据字典首页
    @RequestMapping
    public String index() {
        return "dict/index";
    }

    //获取数据字典的数据
    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result findZnodes(@RequestParam(value = "id", defaultValue = "0") Long id) {
        //调用DictService中查询数据字典中的数据的方法
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        return Result.ok(znodes);
    }

    //根据区域iid获取所有字节点
    @RequestMapping("/findListByParentId/{areaId}")
    @ResponseBody
    public Result findListByParentId(@PathVariable("areaId") Long areaId) {
        //调用dictService中根据父id查询所有子节点的方法
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }

}
