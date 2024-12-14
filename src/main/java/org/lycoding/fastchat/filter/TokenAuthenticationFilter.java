package org.lycoding.fastchat.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.entity.enums.HttpStatusCode;
import org.lycoding.fastchat.service.impl.LoginSeviceImpl;
import org.lycoding.fastchat.utils.JwtUtils;
import org.lycoding.fastchat.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: lycoding
 * @Description: 自定义过滤器实现token校验
 *   添加到spring security过滤器链中
 * @DateTime: 2024/9/17 13:52
 **/
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  RedisCache redisCache;
    @Autowired
    private LoginSeviceImpl loginSevice;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         *  对登录、注册请求放行
         */
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/account/login") || requestURI.equals("/account/register") || requestURI.equals("/account/captcha")){
            filterChain.doFilter(request, response);
            return;
        }
        /**
         * 获取请求头中的令牌，进行校验
         */
        String authorization = request.getHeader("Authorization");
        // token校验
        String value = redisCache.getValue(Constants.TOKEN_STORE+authorization);
        if ("".equals(value) || value==null) {
            // 返回401响应码
            response.setContentType("text/html;charset=UTF-8");  //设置响应体 响应内容格式
            response.setStatus(HttpStatusCode.UNAUTHORIZED.getCode());
            response.getWriter().write(HttpStatusCode.UNAUTHORIZED.getMessage());
            return;
        }
        //解析用户id
        Map<String, Object> map = JwtUtils.parseToken(authorization);
        String userId = (String) map.get("userId");

        /**
         * token校验成功
         */
        UserDetails userDetails = loginSevice.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
