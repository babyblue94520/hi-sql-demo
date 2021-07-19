package pers.clare.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pers.clare.demo.data.jpa.UserJpaRepository;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.demo.data.entity.User;
import pers.clare.demo.data.sql.UserRepository;

import java.util.List;


/**
 * 使用者服務
 */
@Slf4j
@Validated
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> page(Pagination pagination) {
        return userRepository.page(pagination);
    }

    @Transactional
    public User insert(
            User user
    ) {
        long t = System.currentTimeMillis();
        user.setUpdateTime(t);
        user.setUpdateUser(1L);
        user.setCreateTime(t);
        user.setCreateUser(1L);
        insert2(user);
//        return userRepository.insert(user);
//        user.setId(null);
        userRepository.insert(user);
        throw new RuntimeException("test");
    }

    public User insert2(
            User user
    ) {
        long t = System.currentTimeMillis();
        user.setUpdateTime(t);
        user.setUpdateUser(1L);
        user.setCreateTime(t);
        user.setCreateUser(1L);
//        user.setId(null);
        return userRepository.insert(user);
    }

    public int update(
            User user
    ) {
        return userRepository.update(user);
    }

}
