package com.huang.mybatis.test;

import cn.hutool.core.io.resource.ClassPathResource;
import com.huang.mybatis.session.SqlSession;
import com.huang.mybatis.session.SqlSessionFactory;
import com.huang.mybatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @author: hsz
 * @date: 2021/4/14 10:05
 * @description:
 */

public class MybatisTest {

    public static void main(String[] args) {
        InputStream inputStream = new ClassPathResource("mybatis-config.xml").getStream();
        //创建SqlSessionFacory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        User user = mapper.getById(1);
//        System.out.println(user);
        System.out.println(mapper.add(3, "指尖凉"));
        List<User> users = mapper.getAll();
        users.forEach(System.out::println);
    }
}
