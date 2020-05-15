package com.dmm.es.config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/13 17:38
 * @motto The more learn, the more found his ignorance.
 */

public class DBHelper {

    public static final String url = "jdbc:mysql://192.168.7.241:3306/yunny_dev?useSSL=true";
    public static final String name = "com.mysql.cj.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "123456";

    public static Connection conn = null;

    public static Connection getConn() {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            //获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

