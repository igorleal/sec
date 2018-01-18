package com.igor.sec.security;

import com.igor.sec.entity.User;
import com.igor.sec.service.UserService;
import com.igor.sec.vo.TokenResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationService {

    // EXPIRATION_TIME = 1 day
    static final long EXPIRATION_TIME = 8600000;
    static final String SECRET = "izettle";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse response, String username) throws IOException {
        Date expiresIn = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(expiresIn)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        TokenResponseVO tokenResponse = new TokenResponseVO();
        tokenResponse.setToken(JWT);
        tokenResponse.setExpiresIn(expiresIn);

        response.getWriter().append(new ObjectMapper()
                .writeValueAsString(tokenResponse));
        response.setHeader("Content-Type", "application/json");
    }

    static Authentication getAuthentication(HttpServletRequest request, UserService userService) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (username != null) {
                User user = userService.findUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }

}
