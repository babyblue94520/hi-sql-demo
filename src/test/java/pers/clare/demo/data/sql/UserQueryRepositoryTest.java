package pers.clare.demo.data.sql;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.clare.demo.bo.UserPageQuery;
import pers.clare.demo.bo.UserSortQuery;
import pers.clare.demo.data.entity.User;
import pers.clare.demo.vo.SimpleUser;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserQueryRepositoryTest {
    private static final int count = 10;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserQueryRepository userQueryRepository;

    @Test
    @Order(Integer.MIN_VALUE)
    void insert() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            userRepository.insert("test" + i);
        }
    }

    @Test
    void findId() {
        assertNotNull(userQueryRepository.findId());
    }

    @Test
    void find() {
        assertNotNull(userQueryRepository.find());
    }

    @Test
    void find2() {
        assertNotNull(userQueryRepository.find2());
    }

    @Test
    void findAllSimpleMap() {
        List list = userQueryRepository.findAllSimpleMap();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void findAllSimple() {
        List<SimpleUser> list = userQueryRepository.findAllSimple();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void findAllSimpleByIds() {
        List<SimpleUser> list = userQueryRepository.findAllSimple();
        int l = count / 2;
        List<Long> ids = new ArrayList<>(l);
        for (int i = 0; i < l; i++) {
            ids.add(list.get(i).getId());
        }
        list = userQueryRepository.findAllSimple(ids);
        assertNotNull(list);
        assertEquals(l, list.size());
    }

    @Test
    void findAllMap() {
        int size = 5;
        List<Map<String, Object>> list = userQueryRepository.findAllMap(Pagination.of(0, size, "id asc"));
        assertNotNull(list);
        assertEquals(list.size(), size);
        for (Map<String, Object> map : list) {
            assertNotNull(map.get("id"));
        }
    }

    @Test
    void findAll() {
        int size = 5;
        List<SimpleUser> list = userQueryRepository.findAll(Pagination.of(0, size, "id asc"));
        assertNotNull(list);
        assertEquals(list.size(), size);
        for (SimpleUser simpleUser : list) {
            assertNotNull(simpleUser.getId());
        }
    }

    @Test
    void findAllId() {
        int size = 5;
        List<Long> list = userQueryRepository.findAllId(Pagination.of(0, size, "id asc"));
        assertNotNull(list);
        assertEquals(list.size(), size);
        for (Long id : list) {
            assertNotNull(id);
        }
    }

    @Test
    void findAllUser() {
        List<User> list = userQueryRepository.findAll(Sort.of("id asc"));
        assertNotNull(list);
        assertEquals(list.size(), count);
        for (User user : list) {
            assertNotNull(user.getId());
        }
    }

    @Test
    void findAllSimpleSetMap() {
        Set set = userQueryRepository.findAllSimpleSetMap();
        assertNotNull(set);
        assertEquals(set.size(), count);
    }

    @Test
    void findAllSimpleSetMapString() {
        Set set = userQueryRepository.findAllSimpleSetMapString();
        assertNotNull(set);
        assertEquals(set.size(), count);
    }

    @Test
    void findAllTime() {
        int size = 5;
        Set<Long> set = userQueryRepository.findAllTime(Pagination.of(0, size, "id asc"));
        assertNotNull(set);
        assertTrue(set.size() > 0);
    }

    @Test
    void mapPage() {
        int size = 5;
        Page<Map> page = userQueryRepository.mapPage(Pagination.of(0, size, "id asc"));
        assertNotNull(page);
        assertEquals(page.getRecords().size(), size);
    }

    @Test
    void page() {
        int size = 5;
        Page<User> page = userQueryRepository.page(
                ""
                , "and name like :name"
                , Pagination.of(0, size, "id asc")
                , 0L
                , System.currentTimeMillis()
                , null
                , "test%"
        );
        assertNotNull(page);
        assertEquals(page.getRecords().size(), size);
    }

    @Test
    void prepared() {
        int size = 5;
        List<SimpleUser> list = userQueryRepository.findAllSimple(
                "test%"
                , 0
                , size

        );
        assertNotNull(list);
        assertEquals(list.size(), size);
    }

    @Test
    void findAllMapXML() {
        int size = 5;
        List<Map<String, Object>> list = userQueryRepository.findAllMapXML(Pagination.of(0, size, "id asc"));
        assertNotNull(list);
        assertEquals(list.size(), size);
        for (Map<String, Object> map : list) {
            assertNotNull(map.get("id"));
        }
    }

    @Test
    void pageMapXML() {
        int size = 5;
        Page<User> page = userQueryRepository.pageMapXML(
                ""
                , "and name like :name"
                , Pagination.of(0, size, "id asc")
                , 0L
                , System.currentTimeMillis()
                , null
                , "test%"
        );
        assertNotNull(page);
        assertEquals(page.getRecords().size(), size);
    }

    @Test
    void pageQuery() {
        int size = 5;
        UserPageQuery query = new UserPageQuery();
        query.setPagination(Pagination.of(0, size, "id asc"));
        query.setStartTime(0L);
        query.setEndTime(System.currentTimeMillis());
        query.setName("test%");
        Page<User> page = userQueryRepository.page(
                ""
                , "and name like :query.name"
                , query
        );
        assertNotNull(page);
        assertEquals(page.getRecords().size(), size);
    }

    @Test
    void sortQuery() {
        UserSortQuery query = new UserSortQuery();
        query.setSort(Sort.of("id asc"));
        query.setStartTime(0L);
        query.setEndTime(System.currentTimeMillis());
        query.setName("test%");
        List<User> list = userQueryRepository.sort(
                ""
                , "and name like :query.name"
                , query
        );
        assertNotNull(list);
        assertEquals(list.size(), count);
    }

    @Test
    void inQuery() {
        int size = 3;
        List<SimpleUser> simpleUsers = userQueryRepository.findAll(Pagination.of(0, size));
        assertTrue(userQueryRepository.findAll(simpleUsers).size() == size);
        assertTrue(userQueryRepository.findAll(simpleUsers.toArray(new SimpleUser[simpleUsers.size()])).size() == size);

        Object[][] condition = new Object[simpleUsers.size()][];
        int i = 0;
        Object[] values;
        for (SimpleUser user : simpleUsers) {
            values = new Object[2];
            values[0] = user.getId();
            values[1] = user.getName();
            condition[i++] = values;
        }
        assertTrue(userQueryRepository.findAll(condition).size() == size);
    }

    @Test
    void rsCallback() {
        int size = 3;
        List<SimpleUser> simpleUsers = userQueryRepository.findAll(Pagination.of(0, size));

        assertTrue(userQueryRepository.findAll(simpleUsers,(rs)->{
            List<User> result = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                result.add(user);
            }
            return result;
        }).size() == size);
    }

}
