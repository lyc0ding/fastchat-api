package org.lycoding.fastchat.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/7 16:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfo {
    private String groupId;
    private String groupName;
    private String groupHeadman;
    private String groupNotice;
    private String createUser;
    @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    private Date createTime;
    private int joinType;
    private int status;
}
