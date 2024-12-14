package org.lycoding.fastchat.controller;

import org.lycoding.fastchat.entity.pojo.ContactsInfo;
import org.lycoding.fastchat.entity.vo.Result;
import org.lycoding.fastchat.service.ContactsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/25 14:45
 **/
@RestController
@RequestMapping("/contacts")
public class ContactsController extends BaseController{
    @Autowired
    private ContactsInfoService contactsInfoService;
    /**
     * 获取全部联系人信息
     */
    @GetMapping("/list")
    public Result queryContactsByStatus(){
        contactsInfoService.queryContactsByStatus("UbkKyZb1rlnT");
        return getSuccessResponse("查找用户："+"UbkKyZb1rlnT"+"的所有联系人---",null);
    }

    /**
     * 根据id查找某个联系人
     */
    public void queryContactsById(String userId,String queryId){
        ContactsInfo contacts = contactsInfoService.queryContactsById(userId,queryId);
    }

    /**
     * 添加联系人
     */
    @PostMapping("/info")
    public Result addContactsById(@RequestBody ContactsInfo contactsInfo){
        contactsInfoService.addContacts(contactsInfo);
        return getSuccessResponse("添加成功！",null);
    }

}
