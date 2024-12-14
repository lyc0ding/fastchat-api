package org.lycoding.fastchat.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/11 10:22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyInfo {
    private int applyId;
    private String applyUserId;
    private String receiveUserId;
    private String joinId;
    private int applyType;
    private int applyStatus;
    private String applyInfo;
    private Date lastReceiveTime;
}
