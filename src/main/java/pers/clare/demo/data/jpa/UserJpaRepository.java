package pers.clare.demo.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pers.clare.demo.data.entity.User;

public interface UserJpaRepository extends ExtendedRepository<User,Long> {
}
