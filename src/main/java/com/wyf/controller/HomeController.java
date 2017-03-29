package com.wyf.controller;

import com.wyf.model.HostHolder;
import com.wyf.model.ViewObject;
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

//    private List<ViewObject> getUser() {
//        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
//        List<ViewObject> vos = new ArrayList<>();
//        ViewObject vo = new ViewObject();
//        vo.set("user", userService.getUser(localUserId));
//        vos.add(vo);
//        return vos;
//}

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
//        model.addAttribute("vos",getUser());
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
