package org.lycoding.fastchat.annotation;

import java.lang.annotation.*;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/16 10:50
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalInterceptor {
    //为false则不需要校验
    boolean isLogin() default true;  //校验是否是登陆状态

    boolean isAdmin() default false;

}
