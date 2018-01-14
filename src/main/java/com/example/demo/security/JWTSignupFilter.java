package com.example.demo.security;

import com.example.demo.service.UserService;
import com.example.demo.vo.UserRequestVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTSignupFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;

    protected JWTSignupFilter(String url, AuthenticationManager authManager, UserService userService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        UserRequestVO userRequest = new ObjectMapper()
                .readValue(request.getInputStream(), UserRequestVO.class);

        com.example.demo.entity.User user = null;
        try {
            user = userService.signup(userRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Authentication auth = getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            Collections.emptyList()
                    )
            );

            return auth;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) throws IOException {

        TokenAuthenticationService.addAuthentication(response, auth.getName());
    }

}

