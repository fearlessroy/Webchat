package com.wyf.async.handler;

import com.wyf.async.EventHandler;
import com.wyf.async.EventModel;
import com.wyf.async.EventType;
import com.wyf.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by w7397 on 2017/3/30.
 */
@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", model.getExt("username"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆异常", "mails/exception.html",
                map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
