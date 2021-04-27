package pers.clare.demo2.data.sql;

import org.springframework.stereotype.Repository;
import pers.clare.demo2.data.entity.Test;
import pers.clare.hisql.annotation.HiSql;
import pers.clare.hisql.repository.SQLCrudRepository;

@Repository
public interface TestRepository extends SQLCrudRepository<Test> {
    @HiSql("insert into user (name)values(:name)")
    void insert(String name);

    @HiSql("update user set name =:name where id=:id")
    int update(long id, String name);
}
