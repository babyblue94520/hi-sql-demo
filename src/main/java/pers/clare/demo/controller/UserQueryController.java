package pers.clare.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.clare.demo.vo.SimpleUser;
import pers.clare.hisql.page.Page;
import pers.clare.hisql.page.Pagination;
import pers.clare.hisql.page.Sort;
import pers.clare.demo.bo.UserPageQuery;
import pers.clare.demo.bo.UserSortQuery;
import pers.clare.demo.data.entity.User;
import pers.clare.demo.data.sql.UserQueryRepository;
import pers.clare.demo.vo.User2;

import java.sql.ResultSet;
import java.util.*;

@RequestMapping("user/query")
@RestController
public class UserQueryController {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @GetMapping("one/id")
    public Long findId(
    ) throws Exception {
        return userQueryRepository.findId();
    }

    @GetMapping("one")
    public User find(
    ) throws Exception {
        return userQueryRepository.find();
    }

    @GetMapping("one2")
    public User2 find2(
    ) throws Exception {
        return userQueryRepository.find2();
    }

    @GetMapping("map")
    public Collection findAllSimpleMap(
    ) throws Exception {
        return userQueryRepository.findAllSimpleMap();
    }

    @GetMapping("map/2")
    public Collection findAllSimpleMap(
            Pagination pagination
    ) throws Exception {
        return userQueryRepository.findAllMap(pagination);
    }

    @GetMapping
    public Collection findAllSimple(
    ) throws Exception {
        return userQueryRepository.findAllSimple();
    }

    @GetMapping("id")
    public Collection findAllId(
            Pagination pagination
    ) throws Exception {
        return userQueryRepository.findAllId(pagination);
    }

    @GetMapping("set")
    public Collection findAllSimpleSetMap(
    ) throws Exception {
        return userQueryRepository.findAllSimpleSetMap();
    }

    @GetMapping("time")
    public Collection findAllSimpleSetMapString(
            Pagination pagination
    ) throws Exception {
        return userQueryRepository.findAllTime(pagination);
    }

    @GetMapping("sort")
    public List<User> sort(
            Sort sort
    ) throws Exception {
        return userQueryRepository.findAll(sort);
    }

    @GetMapping("page/map")
    public Page mapPage(
            Pagination pagination
    ) throws Exception {
        return userQueryRepository.mapPage(pagination);
    }

    @GetMapping("page")
    public Page<User> page(
            Pagination pagination
            , Long startTime
            , Long endTime
            , Long id
            , String name
    ) throws Exception {
        return userQueryRepository.page(
                id == null ? "" : "and id = :id"
                , StringUtils.isEmpty(name) ? "" : "and name like :name"
                , pagination
                , startTime
                , endTime
                , id
                , name
        );
    }


    @GetMapping("page/1")
    public List<User> page1(String name
    ) throws Exception {
        return userQueryRepository.page1(name);
    }

    @GetMapping("page/2")
    public List<User> page2(String name
    ) throws Exception {
        return userQueryRepository.page2(name);
    }

    @GetMapping("page2")
    public Page<User> page(
            UserPageQuery query
    ) throws Exception {
        return userQueryRepository.page(
                query.getId() == null ? "" : "and id = :query.id"
                , StringUtils.isEmpty(query.getName()) ? "" : "and name like :query.name"
                , query
        );
    }

    @GetMapping("sort2")
    public List<User> sort(
            UserSortQuery query
    ) throws Exception {
        return userQueryRepository.sort(
                query.getId() == null ? "" : "and id = :query.id"
                , StringUtils.isEmpty(query.getName()) ? "" : "and name like :query.name"
                , query
        );
    }

    @GetMapping("2")
    public Collection findAllSimple(
            String name
            , int page
            , int size
    ) throws Exception {
        return userQueryRepository.findAllSimple(name, page, size);
    }

    @GetMapping("xml")
    public Collection findAllMapXML(
            Pagination pagination
    ) throws Exception {
        return userQueryRepository.findAllMapXML(pagination);
    }

    @GetMapping("page/xml")
    public Page<User> pageMapXML(
            Pagination pagination
            , Long startTime
            , Long endTime
            , Long id
            , String name
    ) throws Exception {
        return userQueryRepository.pageMapXML(
                id == null ? "" : "and id = :id"
                , StringUtils.isEmpty(name) ? "" : "and name like :name"
                , pagination
                , startTime
                , endTime
                , id
                , name
        );
    }

    @PostMapping("query/in")
    public Map<String, List<User>> queryIn(
            @RequestBody List<SimpleUser> simpleUsers
    ) throws Exception {
        Map<String, List<User>> result = new HashMap<>();
        result.put("list", userQueryRepository.findAll(simpleUsers));
        result.put("array", userQueryRepository.findAll(simpleUsers.toArray(new SimpleUser[simpleUsers.size()])));
        Object[][] condition = new Object[simpleUsers.size()][];
        int i = 0;
        Object[] values;
        for (SimpleUser user : simpleUsers) {
            values = new Object[2];
            values[0] = user.getId();
            values[1] = user.getName();
            condition[i++] = values;
        }
        result.put("arrays", userQueryRepository.findAll(condition));
        return result;
    }

    @PostMapping("query/callback/rs")
    public List<User> queryCallbackRS(
            @RequestBody List<SimpleUser> simpleUsers
    ) throws Exception {
        return userQueryRepository.findAll(simpleUsers,(rs)->{
            List<User> result = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                result.add(user);
            }
            return result;
        });
    }

    @PostMapping("query/callback/conn")
    public List<User> queryCallbackConn(
            @RequestBody SimpleUser simpleUser
    ) throws Exception {
        return userQueryRepository.connection((conn)->{
            ResultSet rs = conn.createStatement().executeQuery("select * from user where id="+simpleUser.getId());
            List<User> result = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                result.add(user);
            }
            return result;
        });
    }
}
