package pers.clare.demo.data.sql;

import org.springframework.stereotype.Repository;
import pers.clare.demo.data.entity.User;
import pers.clare.hisql.repository.SQLCrudRepository;

@Repository
public interface UserRepository extends SQLCrudRepository<User> {
}
