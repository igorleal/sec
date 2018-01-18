package com.igor.sec.dao;

import com.igor.sec.entity.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findById(Long id);
}
