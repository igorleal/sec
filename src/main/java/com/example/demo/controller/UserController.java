package com.example.demo.controller;

import com.example.demo.entity.LoginHistory;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/me")
    @ResponseBody
    public User getUsers(Authentication auth) {
        return userService.findUserByUsername(auth.getPrincipal().toString());
    }

    @RequestMapping("/login/history")
    @ResponseBody
    public List<LoginHistory> getLoginHistory(Authentication auth) {
        return userService.findLoginHistory(auth.getPrincipal().toString());
    }

}
