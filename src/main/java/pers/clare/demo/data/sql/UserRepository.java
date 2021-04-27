package pers.clare.demo.data.sql;

import org.springframework.stereotype.Repository;
import pers.clare.demo.data.entity.User;
import pers.clare.hisql.annotation.HiSql;
import pers.clare.hisql.repository.SQLCrudRepository;

@Repository
public interface UserRepository extends SQLCrudRepository<User> {
    @HiSql("insert into user (name)values(:name)")
    void insert(String name);

    @HiSql("update user set name =:name where id=:id")
    int update(long id, String name);
}
