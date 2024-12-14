package org.lycoding.fastchat.controller;

import com.mysql.cj.util.StringUtils;
import org.lycoding.fastchat.entity.enums.HttpStatusCode;
import org.lycoding.fastchat.entity.vo.Result;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/5 12:03
 **/
public class BaseController {
    protected static final String STATUS_SUCCESS="success";
    protected static final String STATUS_ERROR="error";

    protected <T> Result getSuccessResponse(String msg,T t){
        Result<T> result = new Result<>();
        result.setStatus(STATUS_SUCCESS);
        result.setCode(HttpStatusCode.OK.getCode());
        result.setMessage(StringUtils.isNullOrEmpty(msg) ? HttpStatusCode.OK.getMessage() : msg);
        result.setData(t);
        return result;
    }

    protected <T> Result getBusinessErrorResponse(T t){
        Result result = new Result<>();
        result.setStatus(STATUS_ERROR);
        result.setCode(HttpStatusCode.BUSINESS_EXCEPTION.getCode());
        result.setMessage(HttpStatusCode.BUSINESS_EXCEPTION.getMessage());
        result.setData(t);
        return result;
    }
//    服务器错误
    protected <T> Result getServerErrorResponse(T t){
        Result result = new Result<>();
        result.setStatus(STATUS_ERROR);
        result.setCode(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode());
        result.setMessage(HttpStatusCode.INTERNAL_SERVER_ERROR.getMessage());
        result.setData(t);
        return result;
    }

    protected <T> Result getContentErrorResponse(){
        Result<T> result = new Result<>();
        result.setStatus(STATUS_ERROR);
        result.setCode(HttpStatusCode.NO_CONTENT.getCode());
        result.setMessage(HttpStatusCode.NO_CONTENT.getMessage());
        return result;
    }

    public <T> Result idNotUserExist(String msg){
        Result<T> result = new Result<>();
        result.setCode(HttpStatusCode.BAD_REQUEST.getCode());
        result.setStatus(STATUS_ERROR);
        result.setMessage(!StringUtils.isNullOrEmpty(msg)?msg:HttpStatusCode.BAD_REQUEST.getMessage());
        return result;
    }
}
