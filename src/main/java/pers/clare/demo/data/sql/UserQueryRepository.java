package pers.clare.demo.data.sql;

import org.springframework.stereotype.Repository;
import pers.clare.hisql.annotation.HiSql;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;
import pers.clare.hisql.repository.SQLRepository;
import pers.clare.demo.bo.UserPageQuery;
import pers.clare.demo.bo.UserSortQuery;
import pers.clare.demo.data.entity.User;
import pers.clare.demo.vo.SimpleUser;
import pers.clare.demo.vo.User2;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface UserQueryRepository extends SQLRepository {
    @HiSql("select id from user")
    Long findId();

    @HiSql("select * from user")
    User find();

    @HiSql(" select id,name,account,email,locked,enabled from user")
    User2 find2();

    @HiSql("select id,name from user limit 0,10")
    List findAllSimpleMap();

    @HiSql(" Select id,name from user limit 0,10")
    List<SimpleUser> findAllSimple();

    @HiSql("select id,name from user where id in :ids limit 0,10")
    List<SimpleUser> findAllSimple(List<Long> ids);

    @HiSql("select id,name from user")
    List<Map<String, Object>> findAllMap(Pagination pagination);

    @HiSql("select id,name from user")
    List<SimpleUser> findAll(Pagination pagination);

    @HiSql("select id from user")
    List<Long> findAllId(Pagination pagination);

    @HiSql("select * from user")
    List<User> findAll(Sort sort);

    @HiSql("select id,name from user limit 0,10")
    Set findAllSimpleSetMap();

    @HiSql("select id,name from user limit 0,10")
    Set<Map<String, String>> findAllSimpleSetMapString();

    @HiSql("select create_time from user")
    Set<Long> findAllTime(Pagination pagination);

    @HiSql("select * from user")
    Page<Map> mapPage(Pagination pagination);


    @HiSql("select * from user where create_time between :startTime and :endTime {andId}{andName}")
    Page<User> page(
            String andId
            , String andName
            , Pagination pagination
            , Long startTime
            , Long endTime
            , Long id
            , String name
    );

    @HiSql("select * from user where name like :name")
    List<User> page1(String name);

    @HiSql("select * from user where name like ?")
    List<User> page2(String name);

    @HiSql("select id,name from user where name like ? limit ?,?")
    List<SimpleUser> findAllSimple(String name, int page, int size);

    /**
     * use method name to get sql from XML
     */
    List<Map<String, Object>> findAllMapXML(Pagination pagination);

    /**
     * use name to get sql from XML
     */
    @HiSql(name = "pageMapXML")
    Page<User> pageMapXML(
            String andId
            , String andName
            , Pagination pagination
            , Long startTime
            , Long endTime
            , Long id
            , String name
    );

    @HiSql("select * from user where create_time between :query.startTime and :query.endTime {andId}{andName}")
    Page<User> page(
            String andId
            , String andName
            , UserPageQuery query
    );

    @HiSql("select * from user where create_time between :query.startTime and :query.endTime {andId}{andName}")
    List<User> sort(String andId, String andName, UserSortQuery query);


    @HiSql("select * from user where (id,name) in :idNames")
    List<User> findAll(SimpleUser[] idNames);

    @HiSql("select * from user where (id,name) in :idNames")
    List<User> findAll(List<SimpleUser> idNames);

    @HiSql("select * from user where (id,name) in :idNames")
    List<User> findAll(Object[][] idNames);
}
