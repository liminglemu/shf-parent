//package com.atguigu;
//
//import com.atguigu.dao.DictDao;
//import entity.Dict;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * @author 柚mingle木
// * @version 1.0
// * @date 2022/8/30 23:08
// */
//@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
//@RunWith(SpringRunner.class)
//public class DictTest {
//    @Autowired
//    private DictDao dictDao;
//
//    //测试根据id查询字节点
//    @Test
//    public void testFindListByParentId() {
//        List<Dict> listByParentId = dictDao.findListByParentId(1L);
//        for (Dict dict : listByParentId) {
//            System.out.println("dict : " + dict);
//        }
//    }
//}
