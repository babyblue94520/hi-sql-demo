package pers.clare.demo2.data.sql;

import org.springframework.stereotype.Repository;
import pers.clare.demo.vo.SimpleUser;
import pers.clare.demo2.data.entity.TestUser;
import pers.clare.hisql.annotation.HiSql;
import pers.clare.hisql.repository.SQLCrudRepository;

import java.util.List;

@Repository
public interface TestUserRepository extends SQLCrudRepository<TestUser> {
    @HiSql("insert into test_user (name)values(:name)")
    void insert(String name);

    @HiSql("update TEST_USER set NAME =:name where ID=:id")
    int update(long id, String name);

    List<TestUser> findAllByIn(TestUser[] testUsers);

    @HiSql("select * from TEST_USER where (ID,NAME) in :testUsers")
    List<TestUser> findAllByIn(List<TestUser> testUsers);

    @HiSql("select * from TEST_USER where (ID,NAME) in :testUsers")
    List<TestUser> findAllByIn(Object[][] testUsers);
}
