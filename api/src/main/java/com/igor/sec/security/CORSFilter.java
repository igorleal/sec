package com.igor.sec.security;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "authorization,content-type");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE");

        chain.doFilter(request, response);
    }
}
