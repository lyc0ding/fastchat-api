package org.lycoding.fastchat.controller;

import org.lycoding.fastchat.entity.pojo.GroupInfo;
import org.lycoding.fastchat.entity.vo.Result;
import org.lycoding.fastchat.service.GroupInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/7 18:09
 **/
@RestController
@RequestMapping("/group")
public class GroupController extends BaseController{
    @Autowired
    private GroupInfoService groupInfoService;

    /**
     * 创建群聊
     */
    @PostMapping("/info")
    public Result createGroups(@RequestBody GroupInfo groupInfo){
        groupInfoService.createGroups(groupInfo);
        return getSuccessResponse("创建成功",null);
    }

    /**
     * 加入群聊
     */
    @PostMapping("join")
    public Result addGroupById(@RequestBody GroupInfo groupInfo){
        groupInfoService.addGroupById(groupInfo);
        return getSuccessResponse("你已经加入群聊！",null);
    }


    /**
     * 修改群聊信息
     */
    @PutMapping("/info")
    public void updateGroupInfo(@RequestBody GroupInfo groupInfo){
//        groupInfoService.updateGroupInfo(groupInfo);
    }

}
