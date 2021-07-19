package pers.clare.demo.data.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pers.clare.demo.data.entity.User;

public interface UserJpaRepository extends ExtendedRepository<User, Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into user (id,account) values (:id,:account)",nativeQuery = true)
    void insert(@Param("id") Long id, @Param("account") String account);
}
