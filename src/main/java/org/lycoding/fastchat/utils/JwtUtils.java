package org.lycoding.fastchat.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.lycoding.fastchat.entity.Constants;

import java.util.Date;
import java.util.Map;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/9 22:24
 **/
public class JwtUtils {

    //    接收业务数据，生成token并返回
    public static String genToken(Map<String,Object> claims ){
        return JWT.create()
                .withClaim("claims",claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*12))//设置token过期时间为12个小时
                .sign(Algorithm.HMAC256(Constants.JWT_KEY));//设置签名
    }
    //接收token，验证token，并返回业务数据
    public static Map<String,Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(Constants.JWT_KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

//    解析出token中的userId
    public static String getUserId(Map<String,Object> map){
        Object o = map.get("userId");
        return o.toString();
    }
}
