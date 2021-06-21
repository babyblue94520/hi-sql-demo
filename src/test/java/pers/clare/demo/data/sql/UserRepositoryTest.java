package pers.clare.demo.data.sql;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.clare.demo.data.entity.User;
import pers.clare.hisql.page.Next;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    private static long insertKey;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Order(1)
    void insert() {
        User user = User.builder()
                .name("test")
                .build();
        userRepository.insert(user);
        insertKey = user.getId();
        assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    void insertAutoKey() {
        User user = User.builder()
                .id(insertKey + 10)
                .name("test1")
                .build();
        userRepository.insert(user);
        assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    @Order(3)
    void count() {
        assertTrue(userRepository.count() > 0);
        assertTrue(userRepository.countById(insertKey) > 0);
        User user = userRepository.findById(insertKey);
        assertTrue(userRepository.count(user) > 0);
    }

    @Test
    @Order(4)
    void update() {
        String name = "update";
        User user = userRepository.findById(insertKey);
        user.setName(name);
        userRepository.update(user);
        user = userRepository.find(user);
        assertEquals(name, user.getName());

        name = "update2";
        userRepository.update(user.getId(), name);
        user = userRepository.find(user);
        assertEquals(name, user.getName());
    }

    @Test
    @Order(4)
    void updatable() {
        User user = userRepository.findById(insertKey);
        user.setCreateUser(2L);
        userRepository.update(user);
        user = userRepository.find(user);
        assertNotEquals(2L, user.getCreateUser());
    }

    @Test
    @Order(5)
    void delete() {
        User user = User.builder()
                .name("test")
                .build();
        userRepository.insert(user);
        userRepository.delete(user);
        user = userRepository.find(user);
        assertNull(user);
    }

    @Test
    @Order(6)
    void deleteById() {
        User user = User.builder()
                .name("test")
                .build();
        userRepository.insert(user);
        userRepository.deleteById(user.getId());
        user = userRepository.find(user);
        assertNull(user);
    }

    @Test
    @Order(100)
    void deleteAll() {
        userRepository.deleteAll();
        assertTrue(userRepository.count() == 0);
    }

    @Test
    @Order(101)
    void batchInsert() {
        deleteAll();
        int count = 5;
        List<User> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(User.builder()
                    .name("test" + i)
                    .build());
        }
        userRepository.insertAll(list);
        assertTrue(userRepository.count() == count);

        User[] array = new User[count];
        for (int i = 0; i < count; i++) {
            array[i] = User.builder()
                    .name("test" + i)
                    .build();
        }
        userRepository.insertAll(array);
        assertTrue(userRepository.count() == count * 2);
    }

    @Test
    @Order(102)
    void batchUpdate() {
        String name = "batchUpdate";
        List<User> list = userRepository.findAll();
        assertTrue(list.size() > 0);
        for (User user : list) {
            user.setName(name);
        }
        userRepository.updateAll(list);
        list = userRepository.findAll();
        for (User user : list) {
            assertEquals(user.getName(), name);
        }

        name = "batchUpdate2";
        User[] array = userRepository.findAll().toArray(new User[list.size()]);
        for (User user : array) {
            user.setName(name);
        }
        userRepository.updateAll(array);
        array = userRepository.findAll().toArray(new User[list.size()]);
        for (User user : array) {
            assertEquals(user.getName(), name);
        }
    }

    @Test
    @Order(103)
    void sort() {
        List<User> list = userRepository.findAll(Sort.of("id asc"));
        User first = list.get(0);
        list = userRepository.findAll(Sort.of("id DESC"));
        User last = list.get(0);
        assertTrue(first.getId() < last.getId());
    }

    @Test
    @Order(104)
    void next() {
        long total = userRepository.count();
        assertTrue(total > 0);
        long count = 0;
        int size = 3;
        Pagination pagination = Pagination.of(0, size);
        Next<User> next;
        do {
            next = userRepository.next(pagination);
            count += next.getRecords().size();
            pagination = pagination.next();
        } while (next.getRecords().size() > 0);
        assertEquals(total, count);
    }


    @Test
    @Order(105)
    void page() {
        long total = userRepository.count();
        assertTrue(total > 0);
        long count = 0;
        int size = 3;
        Pagination pagination = Pagination.of(0, size);
        Page<User> page;
        int recordSize;
        do {
            page = userRepository.page(pagination);
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
        long total = userRepository.count();
        assertTrue(total > 0);
        long count = 0;
        int size = 3;
        Pagination pagination = Pagination.of(0, size, "id asc");
        Page<User> page;
        List<User> records;
        int recordSize;
        do {
            page = userRepository.page(pagination);
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

    @Test
    @Order(107)
    void connection() {
        userRepository.count();
        long total = userRepository.connection((conn) -> {
            ResultSet rs = conn.createStatement().executeQuery("select count(*) from user");
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;
        });

        assertTrue(total == userRepository.count());
    }

    @Test
    @Order(108)
    void preparedStatement() {
        User user = User.builder()
                .name("test")
                .build();
        userRepository.insert(user);
        long id = userRepository.preparedStatement("select * from user where id=?", (ps) -> {
            ps.setLong(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
            return 0L;
        });
        System.out.println(user.getId() + " " + id);
        assertEquals(user.getId(), id);
    }

}
