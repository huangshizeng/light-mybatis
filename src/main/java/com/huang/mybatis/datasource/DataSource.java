package com.huang.mybatis.datasource;


import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: hsz
 * @date: 2021/4/13 16:47
 * @description:
 */

@AllArgsConstructor
public class DataSource {

    private String driver;
    private String url;
    private String username;
    private String password;

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        //建立数据库连接
        return DriverManager.getConnection(url, username, password);
    }
}
