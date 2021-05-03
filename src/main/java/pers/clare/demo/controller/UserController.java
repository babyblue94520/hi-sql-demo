package pers.clare.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.clare.hisql.page.Next;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;
import pers.clare.demo.data.entity.User;
import pers.clare.demo.data.sql.UserRepository;

import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> all(
    ) throws Exception {
        return userRepository.findAll();
    }

    @GetMapping("sort")
    public List<User> all(
            Sort sort
    ) throws Exception {
        return userRepository.findAll(sort);
    }

    @GetMapping("next")
    public Next<User> next(
            Pagination pagination
    ) throws Exception {
        return userRepository.next(pagination);
    }

    @GetMapping("page")
    public Page<User> page(
            Pagination pagination
    ) throws Exception {
        return userRepository.page(pagination);
    }

    @GetMapping("one")
    public User find(
            User user
    ) throws Exception {
        return userRepository.find(user);
    }

    @GetMapping("id")
    public User find(
            Long id
    ) throws Exception {
        return userRepository.findById(id);
    }

    @GetMapping("count")
    public long count(
    ) throws Exception {
        return userRepository.count();
    }

    @GetMapping("count/id")
    public long count(
            Long id
    ) throws Exception {
        return userRepository.countById(id);
    }

    @PostMapping
    public User insert(
            User user
    ) throws Exception {
        return userRepository.insert(user);
    }

    @PostMapping("name")
    public void insert(
            String name
    ) throws Exception {
        userRepository.insert(name);
    }


    @PutMapping
    public User update(
            User user
    ) throws Exception {
        int count = userRepository.update(user);
        return userRepository.find(user);
    }

    @DeleteMapping
    public User delete(
            User user
    ) throws Exception {
        userRepository.delete(user);
        return userRepository.find(user);
    }
}
