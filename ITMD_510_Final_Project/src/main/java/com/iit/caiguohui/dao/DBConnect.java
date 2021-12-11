
package com.iit.caiguohui.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    private static DBConnect conn = null;
    // Code database URL
//    static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/510labs?autoReconnect=true&useSSL=false";
//    // Database credentials
//    static final String USER = "db510", PASS = "510";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/itt?autoReconnect=true&useSSL=false";
    static final String USER = "root", PASS = "China,789";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * get db connect
     */
    public static Connection getConnect() throws SQLException {
        if (conn == null) {
            synchronized (DBConnect.class) {
                if (conn == null) {
                    conn = new DBConnect();
                }
            }
        }
        return conn.connect();
    }

    /**
     * close db connect
     */
    public static void close() {
        if (conn != null) {
            try {
                conn.connect().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    private DBConnect() {

    }
}
