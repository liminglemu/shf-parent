package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.HouseImageService;
import entity.HouseImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import result.Result;
import util.QiniuUtils;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/3 22:20
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    @Reference
    private HouseImageService houseImageService;

    //去上传图片的页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, Map map) {
        //将房源id和图片的类型放到request中
        map.put("houseId", houseId);
        map.put("type", type);
        return "house/upload";
    }

    //上传房源或房产图片
    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) {
        try {
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    //获取字节数组
                    byte[] bytes = file.getBytes();
                    //获取图片的名字
                    String originalFilename = file.getOriginalFilename();
                    //通过UUID随机生成字符串上传图片的名字
                    String newFileName = UUID.randomUUID().toString();
                    //通过七牛工具类上传
                    QiniuUtils.upload2Qiniu(bytes, newFileName);

                    //创建houseImage对象
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    //设置图片路径，路径的格式：http://七牛云的域名/随机生成的图片的名字
                    houseImage.setImageUrl("http://rhmxcwpqq.hn-bkt.clouddn.com/" + newFileName);

                    //调用HouseImageService中保存的方法
                    houseImageService.insert(houseImage);

                    /*
                     * http://rhmxcwpqq.hn-bkt.clouddn.com/34b7ce1e-4179-4892-8dee-a97cdf7c97a2
                     * http://rhmxcwpqq.hn-bkt.clouddn.com34b7ce1e-4179-4892-8dee-a97cdf7c97a2/
                     * */
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @RequestMapping("delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        houseImageService.delete(id);
        return "redirect:/house/" + houseId;
    }
}
