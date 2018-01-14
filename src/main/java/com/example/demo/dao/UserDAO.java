package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

    public User findByLogin(String login);

}
