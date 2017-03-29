package com.wyf.controller;

import com.wyf.model.HostHolder;
import com.wyf.model.User;
import com.wyf.model.ViewObject;
import com.wyf.service.ContactsService;
import com.wyf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w7397 on 2017/3/29.
 */
@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    ContactsService contactsService;

    private List<ViewObject> getFriends(int userId) {
        List<Integer> friendIds = contactsService.getContactsIds(userId);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (int id : friendIds) {
            ViewObject vo = new ViewObject();
            User friend=userService.getUser(id);
            vo.set("friend",friend );
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

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("vos", getFriends(12));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId,
                            @RequestParam(value = "pop", defaultValue = "0") int pop) {
//        model.addAttribute("vos", getUser());
//        model.addAttribute("pop", pop);
        return "home";
    }
}
