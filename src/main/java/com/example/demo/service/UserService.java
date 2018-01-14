package com.example.demo.service;

import com.example.demo.dao.LoginHistoryDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.entity.LoginHistory;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginHistoryDAO loginHistoryDAO;

    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public void registerLogin(String username) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(findUserByUsername(username).getId());
        loginHistory.setDate(new Date());
        loginHistoryDAO.save(loginHistory);
    }

}
