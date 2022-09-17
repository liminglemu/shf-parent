package com.atguigu.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.UserInfoService;
import entity.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;
import result.ResultCodeEnum;
import util.MD5;
import vo.LoginVo;
import vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/5
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    //发送验证码
    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpServletRequest request) {
        //设置验证码为8888
        String code = "8888";
        //将验证码放到session中
        request.getSession().setAttribute("code", code);

        //将验证码响应到前端
        return Result.ok(code);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession session) {
        //获取手机号，密码，昵称等。。。
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();
        //判断空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)) {
            //返回参数错误的消息
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //从session中获取验证码
        Object sessionCode = session.getAttribute("code");
        //判断验证码是否正确
        if (!code.equals(sessionCode)) {
            //返回验证码错误的消息
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        //调用userInfoService判断手机号是否被注册
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (null != userInfo) {
            //返回手机号被注册的消息
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //创建一个userInfo对象插入到数据中
        UserInfo registerUserInfo = new UserInfo();
        registerUserInfo.setPhone(phone);
        registerUserInfo.setNickName(nickName);
        registerUserInfo.setPassword(MD5.encrypt(password));
        registerUserInfo.setStatus(1);

        userInfoService.insert(registerUserInfo);

        return Result.ok();
    }

    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpSession session) {
        //获取手机号和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        //验证空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //根据手机号查询信息
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo == null) {
            //账号不正确
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        //验证密码是否正确
        String encrypt = MD5.encrypt(password);
        System.out.println(encrypt);
        if (!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        //判断用户是否被锁定
        if (userInfo.getStatus() == 0) {
            //用户被锁定
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        //登录成功
        //将用户信息放到session中
        session.setAttribute("user", userInfo);
        //创建一个Map，Map中必须存放nickName的key，value是用户名称
        Map map = new HashMap<>();
        map.put("nickName", userInfo.getNickName());
        map.put(("phone"), userInfo.getPhone());
        return Result.ok(map);
    }

    //退出登录
    @RequestMapping("/logout")
    public Result logout(HttpSession session) {
        //将session域中的对象移除
        session.removeAttribute("user");
        return Result.ok();
    }

}
