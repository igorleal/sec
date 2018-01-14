package com.example.demo.service;

import com.example.demo.dao.UserDAO;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

}
