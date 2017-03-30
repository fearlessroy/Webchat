package com.wyf.dao;

import com.wyf.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static com.wyf.dao.MessageDAO.TABLE_NAME;

/**
 * Created by w7397 on 2017/3/29.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_time,has_del ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String FROM_FIELDS = " from_id ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdTime},#{hasDel})"})
    int addMessage(Message message);

    @Select({"select ", INSERT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversation_id} order by created_time "})
    List<Message> getConversationList(@Param("conversation_id") String conversation_id);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnReadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId}"})
    int getConversationTotalCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select id from ", TABLE_NAME, " where  id=#{id}"})
    String getId(@Param("id") int id);

    @Select({"select ", FROM_FIELDS, " from ", TABLE_NAME, " where  id=#{id}"})
    String getFromId(@Param("id") int id);


    @Delete({"delete from ", TABLE_NAME, " where id=#{id} "})
    void deleteMessageById(int id);

    @Update({"update ", TABLE_NAME, " set has_read=1 where to_id=#{to_id} and conversation_id=#{conversation_id}"})
    void updateMessageStatus(@Param("to_id") int to_id, @Param("conversation_id") String conversation_id);


}
