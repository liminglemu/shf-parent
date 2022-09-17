package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/12
 */
@Configuration//声明当前为一个配置类
@EnableGlobalMethodSecurity(prePostEnabled = true)//开去controller中方法的权限控制
@EnableWebSecurity//开启springSecurity的自动配置，为我们生成一个登录页面
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {
    //在内存中设一个认证的用户名和密码
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("li")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles();
    }*/

    //创建一个密码加密器放到ioc容器中
    @Bean
    public PasswordEncoder getPassWordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //允许iframe的内联页面显示
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //必须调用父类的configure的方法，否则认证过程将失效,除非子类配置了认证方法
        //super.configure(http);
        //运行显示
        http.headers().frameOptions().sameOrigin();

        //配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**", "/login").permitAll().anyRequest().authenticated();
        //自定义登录页面
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");//访问首先访问登录页面，默认成功后访问“/”目录

        //配置登出地址及登出成功后去往的页面
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        //关闭跨域请求伪造
        http.csrf().disable();

        //配置自定义的无权限的处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
