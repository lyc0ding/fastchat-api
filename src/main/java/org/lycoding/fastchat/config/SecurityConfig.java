package org.lycoding.fastchat.config;

import org.lycoding.fastchat.filter.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/8 21:15
 **/
@Configuration
public class SecurityConfig {
    //将token校验过滤器注入ioc容器
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;


    /**
     * 注入一个密码加密器。注册时用于密码加密
     */
    @Bean
    public PasswordEncoder setPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // 配置用户的认证方式
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password") // {noop} 表示不加密
                .roles("USER");
        return authenticationManagerBuilder.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 对请求进行访问控制设置
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                // 设置哪些路径可以直接访问，不需要认证
                .requestMatchers("/account/register", "/account/login", "/account/captcha").permitAll()
                .anyRequest().authenticated() // 其他路径的请求都需要认证
        );
        // 关闭跨站点请求伪造csrf防护
        http.csrf((csrf) -> csrf.disable());
        //关闭session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
