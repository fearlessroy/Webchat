package com.wyf.controller;

import com.wyf.model.Message;
import com.wyf.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by w7397 on 2017/3/29.
 */
@Controller
public class ChatsController {
    private static final Logger logger = LoggerFactory.getLogger(ChatsController.class);

    @Autowired
    MessageService messageService;

    @RequestMapping(path = {"/chats/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String chats(Model model, @RequestParam("userId") int userId,
                        @RequestParam("contactsId") int contactsId,
                        @RequestParam("conversationId") String conversationId,
                        HttpServletResponse response) {
        messageService.updateMessageStatus(userId, conversationId);
        return "ok";
    }
}
