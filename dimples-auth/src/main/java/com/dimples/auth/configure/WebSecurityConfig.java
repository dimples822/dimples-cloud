package com.dimples.auth.configure;

import com.dimples.auth.filter.VerifyFilter;
import com.dimples.auth.service.impl.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * SpringSecurity安全认证配置类
 * 配置security的授权信息
 * Order(2)的作用：
 * 由于ResourceServerConfigurerAdapter的过滤器的顺序是Order(3)，
 * 为了让请求'/oauth/**'的路径先让WebSecurityConfigurerAdapter处理，所以需指定其生效顺序
 * 除此之外的其它请求统一交给资源服务器配置处理
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/5
 */
@Order(2)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private VerifyFilter verifyFilter;


    /**
     * 授权认证管理者实例化
     * 重要
     * 密码模式需要使用到这个Bean
     *
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置基本权限
     * 拦截所有请求路径都被权限进行拦截
     * 指定了该安全配置类只对/oauth/开头的请求有效
     *
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.addFilterBefore(verifyFilter, UsernamePasswordAuthenticationFilter.class)
                .requestMatchers().antMatchers("/oauth/**")
                .and()
                .authorizeRequests().antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * 认证管理配置
     * 连接数据查询用户信息，与数据库中密码比对
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
















