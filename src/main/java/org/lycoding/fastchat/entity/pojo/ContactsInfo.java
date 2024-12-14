package org.lycoding.fastchat.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/13 16:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactsInfo {
    private String userId;  //用户id
    private String contactsId;  // 联系人id
    private String contactsRemarks;  // 联系人备注
    private Integer contactsType;  // 联系人类型：群组、好友
    private Date createTime;  // 创建时间
    private int contactStatus;  // 状态 0：非好友 1：好友 2：已删除好友 3：被好友删除 4：已拉黑好友 5：被好友拉黑
}
