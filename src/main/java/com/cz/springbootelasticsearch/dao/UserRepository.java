package com.cz.springbootelasticsearch.dao;

import com.cz.springbootelasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @ClassName: UserRepository
 * @date: 2019/8/15  9:27
 * @author: guohao
 * @Description:
 */
public interface UserRepository extends ElasticsearchRepository<User, Integer> {

    //自定义的方法
    public List<User> findByNameLike(String name);
}
