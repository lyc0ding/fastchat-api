package org.lycoding.fastchat.entity.enums;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/19 14:54
 **/
public enum UserContactStatusEnum {
    NOT_FRIEND(0,"非好友"),
    FRIEND(1,"好友"),
    DEL_FRIEND(2,"已删除好友"),
    DEL_BY_FRIEND(3,"已被好友删除"),
    BLOCKED_FRIEND(4,"已拉黑好友"),
    BLOCKED_BY_FRIEND(5,"被好友拉黑");


    private final int status;
    private final String message;


    UserContactStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
