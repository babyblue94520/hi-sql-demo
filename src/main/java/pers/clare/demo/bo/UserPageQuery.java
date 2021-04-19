package pers.clare.demo.bo;

import lombok.Getter;
import lombok.Setter;
import pers.clare.hisql.page.Pagination;

@Getter
@Setter
public class UserPageQuery extends PageQuery{
    private Long id;
    private String name;
}
