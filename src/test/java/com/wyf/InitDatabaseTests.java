package com.wyf;

import com.wyf.dao.ContactsDAO;
import com.wyf.dao.LoginTicketDAO;
import com.wyf.dao.MessageDAO;
import com.wyf.dao.UserDAO;
import com.wyf.model.LoginTicket;
import com.wyf.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by w7397 on 2017/3/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebMessageApplication.class)
//@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    ContactsDAO contactsDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    MessageDAO messageDAO;


    @Test
    public void initData() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setUsername(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);

            user.setPassword("newpassword");
            userDAO.updatePassword(user);

            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i + 1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i + 1));
            loginTicketDAO.addTicket(ticket);

            loginTicketDAO.updateStatus(ticket.getTicket(), 2);
        }

        Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));

        Assert.assertEquals(1, loginTicketDAO.selectByTicket("TICKET1").getUserId());
        Assert.assertEquals(2, loginTicketDAO.selectByTicket("TICKET1").getStatus());
        Assert.assertEquals(8, contactsDAO.selectContactsIdByUserId(12).size());
        messageDAO.updateMessageStatus(3, "2_3");
    }


}
