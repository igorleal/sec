package com.example.demo.vo;

import java.io.Serializable;
import java.util.Date;

public class TokenResponseVO implements Serializable {
    private String token;
    private Date expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }
}
