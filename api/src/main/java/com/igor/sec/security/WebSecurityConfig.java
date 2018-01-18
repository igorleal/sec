package com.igor.sec.security;

import com.igor.sec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/h2/*").permitAll()
                .antMatchers("/h2").permitAll()
                .antMatchers("/h2/login").permitAll()
                .antMatchers("/console/*").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/login/history").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new CORSFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), userService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTSignupFilter("/signup", authenticationManager(), userService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(userService),
                        UsernamePasswordAuthenticationFilter.class)


                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource);
    }

}
