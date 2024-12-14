package org.lycoding.fastchat.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/28 16:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatSessionUser {
    private String UserId;
    private String ContactsId;
    private String SessionId;
    private String ContactsRemarks;
    private String lastReceiveMessage;
    private Date lastReceiveTime;
    private Integer MemberCount;

}
