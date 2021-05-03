package pers.clare.demo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.clare.demo2.data.entity.TestUser;
import pers.clare.demo2.data.sql.TestUserRepository;
import pers.clare.hisql.page.Next;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private TestUserRepository testRepository;

    @GetMapping
    public List<TestUser> all(
    ) throws Exception {
        return testRepository.findAll();
    }

    @GetMapping("sort")
    public List<TestUser> all(
            Sort sort
    ) throws Exception {
        return testRepository.findAll(sort);
    }

    @GetMapping("next")
    public Next<TestUser> next(
            Pagination pagination
    ) throws Exception {
        return testRepository.next(pagination);
    }

    @GetMapping("page")
    public Page<TestUser> page(
            Pagination pagination
    ) throws Exception {
        return testRepository.page(pagination);
    }

    @GetMapping("one")
    public TestUser find(
            TestUser test
    ) throws Exception {
        return testRepository.find(test);
    }

    @GetMapping("id")
    public TestUser find(
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
    public TestUser insert(
            TestUser test
    ) throws Exception {
        return testRepository.insert(test);
    }
    @PostMapping("2")
    public List<TestUser> insert2(
            TestUser test
    ) throws Exception {
        testRepository.insert(test.getName());
        return testRepository.findAll();
    }


    @PutMapping
    public TestUser update(
            TestUser test
    ) throws Exception {
        int count = testRepository.update(test);
        return testRepository.find(test);
    }

    @DeleteMapping
    public TestUser delete(
            TestUser test
    ) throws Exception {
        testRepository.delete(test);
        return testRepository.find(test);
    }


    @PostMapping("query/in")
    public Map<String, List<TestUser>> queryIn(
            @RequestBody List<TestUser> testUsers
    ) throws Exception {
        Map<String, List<TestUser>> result = new HashMap<>();
        result.put("list", testRepository.findAllByIn(testUsers));
        result.put("array", testRepository.findAllByIn(testUsers.toArray(new TestUser[testUsers.size()])));
        Object[][] condition = new Object[testUsers.size()][];
        int i = 0;
        Object[] values;
        for (TestUser testUser : testUsers) {
            values = new Object[2];
            values[0] = testUser.getId();
            values[1] = testUser.getName();
            condition[i++] = values;
        }
        result.put("arrays", testRepository.findAllByIn(condition));
        return result;
    }
}
