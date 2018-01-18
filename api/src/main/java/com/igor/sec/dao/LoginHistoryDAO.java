package com.igor.sec.dao;

import com.igor.sec.entity.LoginHistory;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface LoginHistoryDAO extends CrudRepository<LoginHistory, Long> {

    List<LoginHistory> findTop5ByUserIdOrderByDateDesc(Long userId);
    List<LoginHistory> findByUserId(Long userId);

}
