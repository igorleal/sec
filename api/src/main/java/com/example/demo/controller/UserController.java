package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    @ResponseBody
    public UserResponseVO getLoggedUser(Authentication auth) {
        return userService.findById(getUser(auth).getId());
    }

    @GetMapping("/login/history")
    @ResponseBody
    public List<Date> getLoginHistory(Authentication auth) {
        return userService.findLoginHistory(getUser(auth));
    }

    @DeleteMapping("/login/history")
    public void deleteLoginHistory(Authentication auth) {
        userService.deleteLoginHistory(getUser(auth));
    }

    private User getUser(Authentication auth) {
        return (User) auth.getPrincipal();
    }

}
