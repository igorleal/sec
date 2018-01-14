package com.example.demo.service;

import com.example.demo.dao.LoginHistoryDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.entity.LoginHistory;
import com.example.demo.entity.User;
import com.example.demo.vo.UserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginHistoryDAO loginHistoryDAO;

    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public List<Date> findLoginHistory(User user) {
        return loginHistoryDAO.findByUserIdOrderByDateDesc(user.getId())
                .stream().map(LoginHistory::getDate)
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return userDAO.findById(id);
    }

    public void registerLogin(String username) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUser(findUserByUsername(username));
        loginHistory.setDate(new Date());
        loginHistoryDAO.save(loginHistory);
    }

    public User signup(UserRequestVO request) throws Exception {
        User existing = findUserByUsername(request.getUsername().trim());
        if (existing != null) {
            throw new Exception("username already exists");
        }
        User user = new User();
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setUsername(request.getUsername().trim());
        user.setActive(true);

        return userDAO.save(user);
    }

    public void deleteLoginHistory(User user) {
        loginHistoryDAO.delete(loginHistoryDAO.findByUserIdOrderByDateDesc(user.getId()));
    }
}
