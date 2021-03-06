package com.igor.sec.service;

import com.igor.sec.MySecApplication;
import com.igor.sec.entity.User;
import com.igor.sec.exception.MySecException;
import com.igor.sec.service.UserService;
import com.igor.sec.vo.UserRequestVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest(classes = MySecApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findLoginHistory() {
        User user = new User();
        user.setId(1L);
        List<Date> history = userService.findLoginHistory(user);
        assertNotNull(history);
        assertEquals(5, history.size());
        assertEquals(new Date(1483668000000L), history.get(0));
        assertEquals(new Date(1483322400000L), history.get(4));
    }

    @Test
    public void findById() {
        assertTrue(userService.findById(1L).getUsername().equals("igor"));
    }

    @Test(expected = MySecException.class)
    public void signupWithInvalidRequestMustThrowException() {
        UserRequestVO request = new UserRequestVO();
        request.setFirstName("Igor");
        request.setLastName("Leal");
        request.setUsername("igor");
        userService.signup(request);
    }

    @Test(expected = MySecException.class)
    public void signupWithInvalidPasswordMustThrowException() {
        UserRequestVO request = new UserRequestVO();
        request.setFirstName("Igor");
        request.setLastName("Leal");
        request.setUsername("igor");
        request.setPassword("igor");
        userService.signup(request);
    }

    @Test
    public void withValidPasswordMustReturnTrue() {
        assertTrue(userService.isPasswordValid("Igorabc123"));
    }

    @Test
    public void withPasswordWithoutLetterMustReturnFalse() {
        assertFalse(userService.isPasswordValid("123456789"));
    }

    @Test
    public void withPasswordWithoutNumberMustReturnFalse() {
        assertFalse(userService.isPasswordValid("abcdefghijkl"));
    }

    @Test
    public void withPasswordWithLessThanEightCharsMustReturnFalse() {
        assertFalse(userService.isPasswordValid("Igor123"));
    }

    @Test
    public void withEmptyRequestMustReturnFalse() {
        UserRequestVO request = null;
        assertFalse(userService.isRquestValid(request));
    }

    @Test
    public void withRequestWithoutUsernameMustReturnFalse() {
        UserRequestVO request = new UserRequestVO();
        request.setFirstName("Igor");
        request.setLastName("Leal");
        request.setPassword("Igorabc123");
        assertFalse(userService.isRquestValid(request));
    }

    @Test
    public void withRequestWithoutFirstNameMustReturnFalse() {
        UserRequestVO request = new UserRequestVO();
        request.setLastName("Leal");
        request.setUsername("igor");
        request.setPassword("Igorabc123");
        assertFalse(userService.isRquestValid(request));
    }

    @Test
    public void withRequestWithoutLastNameMustReturnFalse() {
        UserRequestVO request = new UserRequestVO();
        request.setFirstName("Igor");
        request.setUsername("igor");
        request.setPassword("Igorabc123");
        assertFalse(userService.isRquestValid(request));
    }

    @Test
    public void withRequestWithoutPasswordMustReturnFalse() {
        UserRequestVO request = new UserRequestVO();
        request.setFirstName("Igor");
        request.setLastName("Leal");
        request.setUsername("igor");
        assertFalse(userService.isRquestValid(request));
    }
}