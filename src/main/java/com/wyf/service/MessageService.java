package com.wyf.service;

import com.wyf.dao.MessageDAO;
import com.wyf.model.Message;
import com.wyf.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by w7397 on 2017/3/29.
 */
@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public int getUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnReadCount(userId, conversationId);
    }

    public String getFromId(int id) {
        return messageDAO.getFromId(id);
    }

    public void updateMessageStatus(int toId, String conversationId) {
        messageDAO.updateMessageStatus(toId, conversationId);
    }

    public List<Message> getConversationList(String conversationId) {
        return messageDAO.getConversationList(conversationId);
    }

    public void deleteMessageById(int id) {
        messageDAO.deleteMessageById(id);
    }

    public Map<String, Object> deleteMesage(int currentId, int messageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (currentId == 0) {
            map.put("msguser", "请登录");
            return map;
        }

        if (StringUtils.isBlank(String.valueOf(messageId))) {
            map.put("msgmid", "删除消息ID不能为空");
            return map;
        }

        String mid = messageDAO.getId(messageId);
        if (mid == null) {
            map.put("msgmessage", "消息不存在");
        }

        map.put("messageId", messageDAO.getId(messageId));
        return map;
    }

}
