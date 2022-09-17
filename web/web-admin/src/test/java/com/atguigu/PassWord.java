package com.atguigu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/12
 */
public class PassWord {
    @Test
    public void testPassWord() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //第一次加密
        String encode1 = bCryptPasswordEncoder.encode("123123");
        System.err.println("加密1结果：" + encode1);
        System.out.println("==========================================");
        String encode2 = bCryptPasswordEncoder.encode("123123");
        System.err.println("加密2结果：" + encode2);

        //进行密码匹配
        boolean matches = bCryptPasswordEncoder.matches("123123", encode1);
        boolean matches1 = bCryptPasswordEncoder.matches("123123", encode1);
        System.out.println("第一个：" + matches + ",第二个：" + matches1);
    }
}
