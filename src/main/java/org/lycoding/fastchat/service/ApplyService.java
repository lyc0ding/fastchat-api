package org.lycoding.fastchat.service;

import org.lycoding.fastchat.entity.pojo.ApplyInfo;

public interface ApplyService {
    /**
     * 用户发起申请
     */
    void createApply(ApplyInfo applyInfo);
}
