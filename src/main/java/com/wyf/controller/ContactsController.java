package com.wyf.controller;

import com.wyf.model.Contacts;
import com.wyf.model.HostHolder;
import com.wyf.service.ContactsService;
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

import java.util.List;
import java.util.Map;

/**
 * Created by w7397 on 2017/3/29.
 */
@Controller
public class ContactsController {
    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);
    @Autowired
    UserService userService;

    @Autowired
    ContactsService contactsService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/addContacts/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String addContacts(@RequestParam("targetuserId") int targetuserId) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        Map<String, Object> map = userService.addContacts(localUserId, targetuserId);
        List<Integer> userIds = contactsService.getUserIds(targetuserId);
        if (!userIds.contains(localUserId)) {
            if (localUserId != 0) {
                try {
                    Contacts contacts = new Contacts();
                    contacts.setUserId(localUserId);
                    contacts.setContactsId(targetuserId);
                    contactsService.addContactsRelationship(contacts);
                    return MessageUtil.getJSONString(0, "添加好友成功");
                } catch (Exception e) {
                    logger.error("添加好友失败" + e.getMessage());
                    return MessageUtil.getJSONString(1, map);
                }
            } else {
                return MessageUtil.getJSONString(1, map);
            }
        } else {
            map.put("error", "已经添加好友，无需再次添加");
            return MessageUtil.getJSONString(1, map);
        }
    }

    @RequestMapping(path = {"/delContacts/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String delContacts(@RequestParam("targetuserId") int targetuserId) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        Map<String, Object> map = userService.addContacts(localUserId, targetuserId);
        List<Integer> userIds = contactsService.getUserIds(targetuserId);
        if (localUserId != 0) {
            if (userIds.contains(localUserId)) {
                try {
                    contactsService.delContactsRelationship(targetuserId);
                    return MessageUtil.getJSONString(0, "删除好友成功");
                } catch (Exception e) {
                    logger.error("删除好友失败" + e.getMessage());
                    return MessageUtil.getJSONString(1, map);
                }
            } else {
                map.put("error", "不是好友，无法删除");
                return MessageUtil.getJSONString(1, map);
            }
        } else {
            return MessageUtil.getJSONString(1, map);
        }
    }
}
