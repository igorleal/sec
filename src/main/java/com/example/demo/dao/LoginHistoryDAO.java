package com.example.demo.dao;

import com.example.demo.entity.LoginHistory;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface LoginHistoryDAO extends CrudRepository<LoginHistory, Long> {

}