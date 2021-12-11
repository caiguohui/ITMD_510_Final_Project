package com.iit.caiguohui.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.iit.caiguohui.model.User;

public class UserDao {

    private static UserDao userDao = null;

    private UserDao() {

    }

    public static UserDao getInstance() {
        if (userDao == null) {
            synchronized (UserDao.class) {
                if (userDao == null) {
                    userDao = new UserDao();
                }
            }
        }
        return userDao;
    }

    private final static String table_name = "t_user";

    private Statement stmt = null;

    /**
     * CREATE TABLE METHOD
     */
    public void createTable() {
        try {
            // Open a connection
            System.out.println("Connecting to database to create Table...");
            System.out.println("Connected database successfully...");
            // Execute create query
            System.out.println("Creating table in given database...");
            stmt = DBConnect.getConnect().createStatement();
            String sql = "CREATE TABLE " + table_name + //
                    "(uid INTEGER NOT NULL AUTO_INCREMENT COMMENT 'ID', " + //
                    " username VARCHAR(30) NOT NULL COMMENT 'user name', " + //
                    " password VARCHAR(32) NOT NULL COMMENT 'password', " + //
                    " role INTEGER NOT NULL COMMENT 'role 1: admin 2: customer', " + //
                    " PRIMARY KEY ( uid ))";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            // Handle errors for JDBC
            e.printStackTrace();
        } finally {
            DBConnect.close();
        }
    }

    /**
     * INSERT INTO METHOD
     */
    public void insert(List<User> robjs) {
        try {
            // Execute a query
            System.out.println("Inserting records into the table...");
            stmt = DBConnect.getConnect().createStatement();
            String sql = null;
            // Include all object data to the database table
            for (User item : robjs) {
                // finish string assignment below to insert all array object data
                // (id, income, pep) into your dat
                sql = "INSERT INTO " + table_name + " (username, password, role)  VALUES ('" + item.getUsername() + "', '" + item.getPassword() + "', '" + item.getRole() + "')";
                stmt.executeUpdate(sql);
            }
            System.out.println("Records inserted!");
            DBConnect.getConnect().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * query user information based on the user name, and return empty if it does
     * not exist
     */
    public User getByUsername(String username) {
        User user = null;
        try {
            stmt = DBConnect.getConnect().createStatement();
            String sql = "SELECT uid, username, password, role from " + table_name + " where username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new User();
                int index = 1;
                user.setUid(rs.getInt(index++));
                user.setUsername(rs.getString(index++));
                user.setPassword(rs.getString(index++));
                user.setRole(rs.getInt(index++));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("db connect error.");
        } finally {
            DBConnect.close();
        }
    }
}
