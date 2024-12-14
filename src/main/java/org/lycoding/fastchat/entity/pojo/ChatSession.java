package org.lycoding.fastchat.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/28 16:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatSession {
    private String sessionId;
    private String lastReceiveMessage;
    private Date lastReceiveTime;
}
