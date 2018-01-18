package com.igor.sec.security;

import com.igor.sec.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpMethod;
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

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;

    protected JWTLoginFilter(String url, AuthenticationManager authManager, UserService userService) {
        super(new AntPathRequestMatcher(url, HttpMethod.POST.name()));
        setAuthenticationManager(authManager);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        com.igor.sec.entity.User credentials = new ObjectMapper()
                .readValue(request.getInputStream(), com.igor.sec.entity.User.class);

        Authentication auth = getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        DigestUtils.sha1Hex(credentials.getPassword()),
                        Collections.emptyList()
                )
        );

        if (auth != null && auth.isAuthenticated()) {
            User user = (User) auth.getPrincipal();
            userService.registerLogin(user.getUsername());
        }

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

