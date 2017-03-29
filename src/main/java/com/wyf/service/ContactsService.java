package com.wyf.service;

import com.wyf.dao.ContactsDAO;
import com.wyf.dao.UserDAO;
import com.wyf.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by w7397 on 2017/3/29.
 */
@Service
public class ContactsService {
    @Autowired
    ContactsDAO contactsDAO;

    public List<Integer> getContactsIds(int userId) {
        List<Integer> contactsIds = contactsDAO.selectContactsIdByUserId(userId);
        return contactsIds;
    }

    public List<Integer> getUserIds(int contactsId) {
        List<Integer> UserIds = contactsDAO.selectUserIdByContactsId(contactsId);
        return UserIds;
    }

    public int addContactsRelationship(Contacts contacts) {
        return contactsDAO.addContacts(contacts);
    }

    public void delContactsRelationship(int contactsId) {
        contactsDAO.deleteByContactsId(contactsId);
    }

}
