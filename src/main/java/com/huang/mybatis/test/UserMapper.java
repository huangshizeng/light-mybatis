package com.huang.mybatis.test;

import com.huang.mybatis.annotation.Param;

import java.util.List;

/**
 * @author: hsz
 * @date: 2021/4/14 10:29
 * @description:
 */

public interface UserMapper {

    boolean add(@Param("id") int id, @Param("name") String name);

    User getById(@Param("id") int id);

    List<User> getAll();
}
