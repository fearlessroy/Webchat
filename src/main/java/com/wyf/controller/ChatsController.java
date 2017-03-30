package com.wyf.controller;

import com.wyf.model.HostHolder;
import com.wyf.model.Message;
import com.wyf.model.User;
import com.wyf.model.ViewObject;
import com.wyf.service.MessageService;
import com.wyf.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by w7397 on 2017/3/29.
 */
@Controller
public class ChatsController {
    private static final Logger logger = LoggerFactory.getLogger(ChatsController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    private List<ViewObject> getChats(String conversationId) {
        List<Message> messageLists = messageService.getConversationList(conversationId);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messageLists) {
            //String conversationId = id < userId ? String.format("%d_%d", id, userId) : String.format("%d_%d", userId, id);
            ViewObject vo = new ViewObject();
            User contacts = userService.getUser(message.getFromId());
            User localUser = userService.getUser(localUserId);
            vo.set("currentId", localUserId);
            vo.set("contacts", contacts);
            vo.set("toId", message.getToId());
            vo.set("fromId", message.getFromId());
            vo.set("content", message.getContent());
            vo.set("time", message.getCreatedTime());
            vo.set("contactsHeadUrl", contacts.getHeadUrl());
            vo.set("userHeadUrl", localUser.getHeadUrl());
            //vo.set("user", userService.getUser(news.getUserId()));

//            if (localUserId != 0) {
//                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
//
//            } else {
//                vo.set("like", 0);
//            }
            vos.add(vo);
        }
        return vos;
    }


    @RequestMapping(path = {"/chats/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String chats(Model model, @RequestParam("userId") int userId,
                        @RequestParam("contactsId") int contactsId,
                        @RequestParam("conversationId") String conversationId,
                        HttpServletResponse response) {
        messageService.updateMessageStatus(userId, conversationId);
        ViewObject vo1=new ViewObject();
        vo1.set("targetId",contactsId);
        model.addAttribute("vos", getChats(conversationId));
        model.addAttribute("vo1", vo1);
        return "chats";
//        return String.format("redirect:/chats/?userId=%d&&contactsId=%d&&conversationId=%s",userId,contactsId,conversationId);
    }
}
