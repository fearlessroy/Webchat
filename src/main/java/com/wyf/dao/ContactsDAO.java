package com.wyf.dao;

import com.wyf.model.Contacts;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by w7397 on 2017/3/29.
 */
@Mapper
public interface ContactsDAO {
    String TABLE_NAME = "contacts";
    String INSERT_FIELDS = " user_id,contacts_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String FRIEND_FIELDS = " contacts_id ";
    String USER_FIELDS = " user_id ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{contactsId})"})
    int addContacts(Contacts contacts);

    @Select({"select ", USER_FIELDS, " from ", TABLE_NAME, " where contacts_id=#{contacts_id}"})
    List<Integer> selectUserIdByContactsId(int contacts_id);

    @Select({"select ", FRIEND_FIELDS, " from ", TABLE_NAME, " where user_id=#{userId}"})
    List<Integer> selectContactsIdByUserId(int userId);

    @Delete({"delete from ", TABLE_NAME, " where contacts_id=#{contacts_id}"})
    void deleteByContactsId(int contacts_id);


}
