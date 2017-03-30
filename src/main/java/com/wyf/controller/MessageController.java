package com.wyf.controller;

import com.wyf.model.Contacts;
import com.wyf.model.HostHolder;
import com.wyf.model.Message;
import com.wyf.service.ContactsService;
import com.wyf.service.MessageService;
import com.wyf.service.UserService;
import com.wyf.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by w7397 on 2017/3/29.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    UserService userService;

    @Autowired
    ContactsService contactsService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @RequestMapping(path = {"/msg/sendMessage/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String sendMessage(@RequestParam("targetuserId") int targetuserId,
                              @RequestParam("content") String content) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        Map<String, Object> map = userService.addContacts(localUserId, targetuserId);
        List<Integer> AcontactsIds = contactsService.getContactsIds(localUserId);
        List<Integer> BcontactsIds = contactsService.getContactsIds(targetuserId);
        String conversationId = localUserId < targetuserId ? String.format("%d_%d", localUserId, targetuserId) : String.format("%d_%d", targetuserId, localUserId);
        if (localUserId != 0) {
            if (AcontactsIds.contains(targetuserId)) {
                try {
                    Message message = new Message();
                    message.setFromId(localUserId);
                    message.setToId(targetuserId);
                    message.setContent(content);
                    message.setConversationId(conversationId);
                    message.setCreatedTime(new Date());
                    messageService.addMessage(message);
                    if (!BcontactsIds.contains(localUserId)) {
                        try {
                            Contacts contacts = new Contacts();
                            contacts.setUserId(targetuserId);
                            contacts.setContactsId(localUserId);
                            contactsService.addContactsRelationship(contacts);
                        } catch (Exception e) {
                            logger.error("添加联系人失败" + e.getMessage());
                            return MessageUtil.getJSONString(1, map);
                        }
                    }
                    //return MessageUtil.getJSONString(0, "发送消息成功");
                } catch (Exception e) {
                    logger.error("发送消息失败" + e.getMessage());
                    return MessageUtil.getJSONString(1, map);
                }
            } else {
                map.put("error", "不是联系人无法发送信息");
                return MessageUtil.getJSONString(1, map);
            }
        } else {
            return MessageUtil.getJSONString(1, map);
        }
        return String.format("redirect:/chats/?userId=%d&&contactsId=%d&&conversationId=%s", localUserId, targetuserId, conversationId);

    }

    @RequestMapping(path = {"/msg/delMessage/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String delMessage(@RequestParam("messageId") int messageId) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        Map<String, Object> map = messageService.deleteMesage(localUserId, messageId);
        String messageFromId = messageService.getFromId(messageId);
        if (String.valueOf(localUserId).equals(messageFromId)) {
            try {
                messageService.deleteMessageById(messageId);
                return MessageUtil.getJSONString(0, map);
            } catch (Exception e) {
                logger.error("删除消息失败");
                return MessageUtil.getJSONString(1, map);
            }
        } else {
            map.put("error", "不存在该消息");
            return MessageUtil.getJSONString(1, map);
        }
    }
}
