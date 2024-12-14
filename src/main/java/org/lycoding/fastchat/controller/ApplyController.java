package org.lycoding.fastchat.controller;

import org.lycoding.fastchat.entity.pojo.ApplyInfo;
import org.lycoding.fastchat.entity.vo.Result;
import org.lycoding.fastchat.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/11 10:34
 **/
@RestController
@RequestMapping("/apply")
public class ApplyController extends BaseController{
    @Autowired
    private ApplyService applyService;
    /**
     * 申请加好友、加群
     */
    @PostMapping("/info")
    public Result createApply(@RequestBody ApplyInfo applyInfo){
        applyService.createApply(applyInfo);
        return getSuccessResponse("已提交申请！","");
    }

}
