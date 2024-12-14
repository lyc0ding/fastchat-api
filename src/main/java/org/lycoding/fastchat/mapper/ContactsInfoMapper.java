package org.lycoding.fastchat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lycoding.fastchat.entity.pojo.ContactsInfo;

import java.util.List;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/13 16:33
 **/
@Mapper
public interface ContactsInfoMapper {
    /**
     * 添加联系人
     */
    @Insert("insert into user_contacts(user_id,contacts_id,contacts_type,create_time) value(#{userId},#{contactsId},#{contactsType},now())")
    public void addContacts(ContactsInfo contacts);

    /**
     * 根据用户id 查询所有正常状态联系人
     */
    @Select("select contacts_id from user_contacts where user_id = #{userId} and contacts_status = 1")
    public List<ContactsInfo> queryContactsByStatus(String userId);

    /**
     * 查询正常状体以及已拉黑的联系人
     * 登录成功：存进redis
     */
    @Select("select contacts_id from user_contacts where user_id=#{userId}")
    public List<String> queryAllContacts(String userId);

    /**
     * 根据id查找联系人
     */
    @Insert("")
    ContactsInfo queryContactsById();

    /**
     * 添加联系人
     */
    @Insert("insert into user_contacts(user_id,contacts_id,contacts_remarks,contacts_type,create_time) " +
            "values(#{userId},#{contactsId},#{contactsRemarks},#{contactsType},now())")
    void addContactsById(ContactsInfo contactsInfo);
}
