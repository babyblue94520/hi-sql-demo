package pers.clare.demo.bo;

import lombok.Getter;
import lombok.Setter;
import pers.clare.hisql.page.Pagination;

@Getter
@Setter
public class PageQuery extends TimeRangeQuery{
    private Pagination pagination;
}
