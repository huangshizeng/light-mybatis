package com.huang.mybatis.session;

/**
 * @author: hsz
 * @date: 2021/4/13 16:13
 * @description:
 */

public interface SqlSessionFactory {

    SqlSession openSession();
}
