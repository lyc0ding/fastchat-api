package org.lycoding.fastchat.advice;

import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.lycoding.fastchat.annotation.GlobalInterceptor;
import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.entity.enums.HttpStatusCode;
import org.lycoding.fastchat.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/16 10:51
 **/
@Aspect
@Component
public class GlobalInterceptorAspect {
    @Autowired
    private RedisCache redisCache;

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public GlobalInterceptorAspect(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Before("@annotation(org.lycoding.fastchat.annotation.GlobalInterceptor)")
    public void checkToken(JoinPoint point) throws IOException {
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
        if (interceptor==null) {
            return;
        }
        if (interceptor.isLogin()){
            String token = request.getHeader("token");
            if (StringUtils.isNullOrEmpty(redisCache.getValue(Constants.TOKEN_STORE+token))) {
                /**
                 * 如果未在redis中查询到这样一个token  则抛出一个未认证的异常
                 * 定义全局的Authorization异常，设置响应信息 、 响应码
                 */
                response.setStatus(HttpStatusCode.UNAUTHORIZED.getCode());
                response.getWriter().write(HttpStatusCode.UNAUTHORIZED.getMessage());
            }
        }
    }
}
