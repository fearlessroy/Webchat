package com.wyf.dao;

import com.wyf.model.Friend;
import com.wyf.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by w7397 on 2017/3/29.
 */
@Mapper
public interface FriendDAO {
    String TABLE_NAME = "friend";
    String INSERT_FIELDS = " user_id,friend_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{friendId})"})
    int addFriend(Friend friend);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where userId=#{userId}"})
    List<User> selectFriendsByUserId(int userId);

    @Delete({"delete from ", TABLE_NAME, " where friendId=#{friend_id}"})
    void deleteByFriendId(int friend_id);


}
