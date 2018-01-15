package com.example.demo.service;

import com.example.demo.dao.LoginHistoryDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.entity.LoginHistory;
import com.example.demo.entity.User;
import com.example.demo.exception.MySecException;
import com.example.demo.vo.UserRequestVO;
import com.example.demo.vo.UserResponseVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public UserResponseVO findById(Long id) {
        User user = userDAO.findById(id);
        return new UserResponseVO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }

    public void registerLogin(String username) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUser(findUserByUsername(username));
        loginHistory.setDate(new Date());
        loginHistoryDAO.save(loginHistory);
    }

    public User signup(UserRequestVO request) throws MySecException {
        User existing = findUserByUsername(request.getUsername().trim());
        if (existing != null) {
            throw new MySecException(HttpStatus.BAD_REQUEST, "username already exists");
        }
        User user = new User();
        String encrypted = DigestUtils.sha1Hex(request.getPassword());
        user.setPassword(encrypted);
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
