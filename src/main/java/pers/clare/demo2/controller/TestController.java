package pers.clare.demo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.clare.demo2.data.entity.Test;
import pers.clare.demo2.data.sql.TestRepository;
import pers.clare.hisql.page.Next;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;

import java.util.List;

@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping
    public List<Test> all(
    ) throws Exception {
        return testRepository.findAll();
    }

    @GetMapping("sort")
    public List<Test> all(
            Sort sort
    ) throws Exception {
        return testRepository.findAll(sort);
    }

    @GetMapping("next")
    public Next<Test> next(
            Pagination pagination
    ) throws Exception {
        return testRepository.next(pagination);
    }

    @GetMapping("page")
    public Page<Test> page(
            Pagination pagination
    ) throws Exception {
        return testRepository.page(pagination);
    }

    @GetMapping("one")
    public Test find(
            Test test
    ) throws Exception {
        return testRepository.find(test);
    }

    @GetMapping("id")
    public Test find(
            Long id
    ) throws Exception {
        return testRepository.findById(id);
    }

    @GetMapping("count")
    public long count(
    ) throws Exception {
        return testRepository.count();
    }

    @GetMapping("count/id")
    public long count(
            Long id
    ) throws Exception {
        return testRepository.countById(id);
    }

    @PostMapping
    public Test insert(
            Test test
    ) throws Exception {
        return testRepository.insert(test);
    }

    @PutMapping
    public Test update(
            Test test
    ) throws Exception {
        int count = testRepository.update(test);
        return testRepository.find(test);
    }

    @DeleteMapping
    public Test delete(
            Test test
    ) throws Exception {
        testRepository.delete(test);
        return testRepository.find(test);
    }
}
