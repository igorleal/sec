package com.example.demo.service;

import com.example.demo.dao.LoginHistoryDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.entity.LoginHistory;
import com.example.demo.entity.User;
import com.example.demo.exception.MySecException;
import com.example.demo.vo.UserRequestVO;
import com.example.demo.vo.UserResponseVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
        return loginHistoryDAO.findTop5ByUserIdOrderByDateDesc(user.getId())
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

        if (!isRquestValid(request)) {
            throw new MySecException(HttpStatus.BAD_REQUEST, "All fields are required.");
        }

        if (!isPasswordValid(request.getPassword())) {
            throw new MySecException(HttpStatus.BAD_REQUEST, "Invalid password. It must contain at least 8 characters, including one letter and one digit.");
        }

        User existing = findUserByUsername(request.getUsername().trim());
        if (existing != null) {
            throw new MySecException(HttpStatus.BAD_REQUEST, "Username already registered.");
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

    /*
        Password must contain at least eight characters and at least one number and letters
     */
    protected boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[A-z]).{8,}$");
    }

    protected boolean isRquestValid(UserRequestVO request) {
        return request != null
                && StringUtils.isNoneBlank(request.getUsername(), request.getPassword(), request.getFirstName(), request.getLastName());
    }

    public void deleteLoginHistory(User user) {
        loginHistoryDAO.delete(loginHistoryDAO.findByUserId(user.getId()));
    }
}
