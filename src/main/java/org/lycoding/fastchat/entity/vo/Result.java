package org.lycoding.fastchat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/5 10:05
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private String status;
    private Integer code;
    private String message;//提示信息
    private T data;//响应数据，泛型T，
}
