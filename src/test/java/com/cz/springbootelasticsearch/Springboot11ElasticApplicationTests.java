package com.cz.springbootelasticsearch;

import com.cz.springbootelasticsearch.dao.UserRepository;
import com.cz.springbootelasticsearch.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: Springboot11ElasticApplicationTests
 * @date: 2019/8/15  9:30
 * @author: guohao
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot11ElasticApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test01(){
        for (User user : userRepository.findByNameLike("o")) {
            System.out.println(user);
        }
    }
}
