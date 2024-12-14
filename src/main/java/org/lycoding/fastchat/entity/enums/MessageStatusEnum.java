package org.lycoding.fastchat.entity.enums;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/10/10 12:08
 **/
public enum MessageStatusEnum {
    SENDING(0,"发送中"),
    SENT(1,"已发送");

    MessageStatusEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }

    private int code;
    private String status;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
