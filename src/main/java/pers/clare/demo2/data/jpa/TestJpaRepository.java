package pers.clare.demo2.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.clare.demo2.data.entity.Test;

public interface TestJpaRepository extends JpaRepository<Test,Long> {
}
