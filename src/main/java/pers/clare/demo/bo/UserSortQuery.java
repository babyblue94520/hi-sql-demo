package pers.clare.demo.bo;

import lombok.Getter;
import lombok.Setter;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;

@Getter
@Setter
public class UserSortQuery {
    private Sort sort;
    private Long startTime;
    private Long endTime;
    private Long id;
    private String name;
}
