package pers.clare.demo.data.sql;

import org.springframework.stereotype.Repository;
import pers.clare.hisql.annotation.HiSql;
import pers.clare.hisql.repository.SQLRepository;

@Repository
public interface ConnectionReuseRepository extends SQLRepository {

    // mysql
    @HiSql("update user set name = if(@name:=name,:name,:name) where id=:id")
    int updateName(Long id, String name);

    @HiSql("select @name")
    String getOldName();
}
