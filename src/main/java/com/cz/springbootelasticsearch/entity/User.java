package com.cz.springbootelasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * @ClassName: User
 * @date: 2019/8/15  9:20
 * @author: guohao
 * @Description:
 */
@Document(indexName = "user",type = "docs",shards = 1,replicas = 0)
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private Date updateTime;

    public User(Integer id, String name, Integer age, String email, Date updateTime) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
