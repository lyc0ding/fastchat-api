package org.lycoding.fastchat.service.impl;

import org.lycoding.fastchat.mapper.ApplyInfoMapper;
import org.lycoding.fastchat.entity.pojo.ApplyInfo;
import org.lycoding.fastchat.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/11 10:41
 **/
@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyInfoMapper applyMapper;

    /**
     * 用户发起申请
     *
     * @param applyInfo
     */
    @Override
    public void createApply(ApplyInfo applyInfo) {
        applyMapper.createApply(applyInfo);
    }
}
