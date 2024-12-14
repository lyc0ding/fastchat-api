package org.lycoding.fastchat.entity.enums;

import org.apache.ibatis.type.IntegerTypeHandler;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/29 12:45
 **/
public enum MessageTypeEnum {
    INIT(0,"","连接ws获取信息"),
    ADD_FRIEND(1,"","添加好友打招呼消息"),
    CHAT(2,"","普通聊天消息"),
    GROUP_CREATE(3,"群组已经创建好了，可以和群友一起聊天了","群组创建成功"),
    CONTACTS_APPLY(4,"","好友申请"),
    MEDIA_CHAT(5,"","媒体文件"),
    FILE_UPLOAD(6,"","文件上传"),
    FORCE_OFF_LINE(7,"","强制下线"),
    ADD_GROUP(8,"%s已加入群组","加入群组"),
    DISSOLUTION_GROUP(9,"群组已解散！","解散群组"),
    GROUP_NAME_UPDATE(10,"","更改群名成功"),
    LEAVE_GROUP(11,"%s已退出群聊","退出群聊"),
    REMOVE_GROUP(12,"%s被管理员移出群聊","移除群聊");

    private Integer type;
    private String initMessage;
    private String desc;

    MessageTypeEnum(Integer type,String initMessage,String desc){
        this.type=type;
        this.initMessage=initMessage;
        this.desc=desc;
    }

    public static MessageTypeEnum getByType(Integer type){
        for (MessageTypeEnum item : MessageTypeEnum.values()){
            if (item.getType().equals(type)){
                return item;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getInitMessage() {
        return initMessage;
    }

    public String getDesc() {
        return desc;
    }
}
