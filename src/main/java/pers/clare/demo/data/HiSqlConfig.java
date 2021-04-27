package pers.clare.demo.data;

import pers.clare.hisql.annotation.EnableHiSql;

@EnableHiSql(
        basePackages = "pers.clare.demo.data.sql"
)
public class HiSqlConfig {
}
