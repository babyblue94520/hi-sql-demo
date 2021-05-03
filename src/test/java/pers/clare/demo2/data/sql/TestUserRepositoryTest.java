package pers.clare.demo2.data.sql;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.clare.demo2.data.entity.TestUser;
import pers.clare.hisql.page.Next;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserRepositoryTest {
    private static long insertKey;

    @Autowired
    private TestUserRepository testUserRepository;


    @Test
    @Order(1)
    void insert() {
        TestUser testUser = TestUser.builder()
                .name("test")
                .build();
        testUserRepository.insert(testUser);
        insertKey = testUser.getId();
        assertNotNull(testUser.getId());
    }

    @Test
    @Order(2)
    void insertAutoKey() {
        TestUser testUser = TestUser.builder()
                .id(insertKey + 10)
                .name("test1")
                .build();
        testUserRepository.insert(testUser);
        assertNotNull(testUserRepository.findById(testUser.getId()));
    }

    @Test
    @Order(3)
    void count() {
        assertTrue(testUserRepository.count() > 0);
        assertTrue(testUserRepository.countById(insertKey) > 0);
        TestUser testUser = testUserRepository.findById(insertKey);
        assertTrue(testUserRepository.count(testUser) > 0);
    }

    @Test
    @Order(4)
    void update() {
        String name = "update";
        TestUser testUser = testUserRepository.findById(insertKey);
        testUser.setName(name);
        testUserRepository.update(testUser);
        testUser = testUserRepository.find(testUser);
        assertEquals(name, testUser.getName());

        name = "update2";
        testUserRepository.update(testUser.getId(), name);
        testUser = testUserRepository.find(testUser);
        assertEquals(name, testUser.getName());
    }

    @Test
    @Order(5)
    void delete() {
        TestUser testUser = TestUser.builder()
                .name("test")
                .build();
        testUserRepository.insert(testUser);
        testUserRepository.delete(testUser);
        testUser = testUserRepository.find(testUser);
        assertNull(testUser);
    }

    @Test
    @Order(6)
    void deleteById() {
        TestUser testUser = TestUser.builder()
                .name("test")
                .build();
        testUserRepository.insert(testUser);
        testUserRepository.deleteById(testUser.getId());
        testUser = testUserRepository.find(testUser);
        assertNull(testUser);
    }

    @Test
    @Order(100)
    void deleteAll() {
        testUserRepository.deleteAll();
        assertTrue(testUserRepository.count() == 0);
    }

    @Test
    @Order(101)
    void batchInsert() {
        deleteAll();
        int count = 5;
        List<TestUser> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(TestUser.builder()
                    .name("test" + i)
                    .build());
        }
        testUserRepository.insertAll(list);
        assertTrue(testUserRepository.count() == count);

        TestUser[] array = new TestUser[count];
        for (int i = 0; i < count; i++) {
            array[i] = TestUser.builder()
                    .name("test" + i)
                    .build();
        }
        testUserRepository.insertAll(array);
        assertTrue(testUserRepository.count() == count * 2);
    }

    @Test
    @Order(102)
    void batchUpdate() {
        String name = "batchUpdate";
        List<TestUser> list = testUserRepository.findAll();
        assertTrue(list.size() > 0);
        for (TestUser testUser : list) {
            testUser.setName(name);
        }
        testUserRepository.updateAll(list);
        list = testUserRepository.findAll();
        for (TestUser testUser : list) {
            assertEquals(testUser.getName(), name);
        }

        name = "batchUpdate2";
        TestUser[] array = testUserRepository.findAll().toArray(new TestUser[list.size()]);
        for (TestUser testUser : array) {
            testUser.setName(name);
        }
        testUserRepository.updateAll(array);
        array = testUserRepository.findAll().toArray(new TestUser[list.size()]);
        for (TestUser testUser : array) {
            assertEquals(testUser.getName(), name);
        }
    }

    @Test
    @Order(103)
    void sort() {
        List<TestUser> list = testUserRepository.findAll(Sort.of("id asc"));
        TestUser first = list.get(0);
        list = testUserRepository.findAll(Sort.of("id DESC"));
        TestUser last = list.get(0);
        assertTrue(first.getId() < last.getId());
    }

    @Test
    @Order(104)
    void next() {
        long total = testUserRepository.count();
        assertTrue(total > 0);
        long count = 0;
        int size = 3;
        Pagination pagination = Pagination.of(0, size);
        Next<TestUser> next;
        do {
            next = testUserRepository.next(pagination);
            count += next.getRecords().size();
            pagination = pagination.next();
        } while (next.getRecords().size() > 0);
        assertEquals(total, count);
    }


    @Test
    @Order(105)
    void page() {
        long total = testUserRepository.count();
        assertTrue(total > 0);
        long count = 0;
        int size = 3;
        Pagination pagination = Pagination.of(0, size);
        Page<TestUser> page;
        int recordSize;
        do {
            page = testUserRepository.page(pagination);
            assertEquals(total, page.getTotal());
            recordSize = page.getRecords().size();
            count += recordSize;
            pagination = pagination.next();
        } while (recordSize > 0);
        assertEquals(total, count);
    }

    @Test
    @Order(106)
    void pageAndSort() {
        long total = testUserRepository.count();
        assertTrue(total > 0);
        long count = 0;
        int size = 3;
        Pagination pagination = Pagination.of(0, size, "id asc");
        Page<TestUser> page;
        List<TestUser> records;
        int recordSize;
        do {
            page = testUserRepository.page(pagination);
            assertEquals(total, page.getTotal());
            records = page.getRecords();
            recordSize = records.size();
            count += recordSize;
            if (recordSize == size) {
                assertTrue(records.get(0).getId() < records.get(size - 1).getId());
            }
            pagination = pagination.next();
        } while (recordSize > 0);
        assertEquals(total, count);
        deleteAll();
    }
}
