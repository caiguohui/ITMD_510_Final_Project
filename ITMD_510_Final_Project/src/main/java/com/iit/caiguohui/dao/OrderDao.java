package com.iit.caiguohui.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.iit.caiguohui.model.Order;

public class OrderDao {

    private static OrderDao orderDao = null;

    private OrderDao() {

    }

    public static OrderDao getInstance() {
        if (orderDao == null) {
            synchronized (OrderDao.class) {
                if (orderDao == null) {
                    orderDao = new OrderDao();
                }
            }
        }
        return orderDao;
    }

    private final static String table_name = "t_order";

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
            String sql = "CREATE TABLE " + table_name + //
                    "(order_no VARCHAR(32) NOT NULL COMMENT 'order no', " + //
                    " phone VARCHAR(11) NOT NULL COMMENT 'phone', " + //
                    " name VARCHAR(30) NOT NULL COMMENT 'name', " + //
                    " amount decimal(10,2) NOT NULL COMMENT 'amount', " + //
                    " time datetime NOT NULL COMMENT 'create time', " + //
                    " PRIMARY KEY ( order_no ))";
            DBConnect.getConnect().createStatement().executeUpdate(sql);
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
    public void insert(List<Order> robjs) {
        try {
            // Execute a query
            System.out.println("Inserting records into the table...");
            String sql = null;
            // Include all object data to the database table
            for (Order item : robjs) {
                // finish string assignment below to insert all array object data
                // (id, income, pep) into your dat
                sql = "INSERT INTO " + table_name + " (order_no, phone, amount, name, time)  VALUES (" //
                        + "'" + item.getOrderNo() + "'" //
                        + ", '" + item.getPhone() + "' "//
                        + ", " + item.getAmount()//
                        + ", '" + item.getName() + "'"//
                        + ", '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getTime()) + "')";
                DBConnect.getConnect().createStatement().executeUpdate(sql);
            }
            System.out.println("Records inserted!");
            DBConnect.getConnect().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * query oerder list
     */
    public List<Order> getList(String orderNo, String phone) {
        try {
            StringBuffer sqlSB = new StringBuffer();
            sqlSB.append("SELECT order_no, phone, amount, name, time from " + table_name + " where 1 = 1");
            if (orderNo != null && orderNo.trim().length() > 0) {
                sqlSB.append(" and order_no = '" + orderNo + "'");
            }
            if (phone != null && phone.trim().length() > 0) {
                sqlSB.append(" and phone = '" + phone + "'");
            }
            List<Order> result = new ArrayList<>();
            ResultSet rs = DBConnect.getConnect().createStatement().executeQuery(sqlSB.toString());
            while (rs.next()) {
                int index = 1;
                Order order = new Order();
                order.setOrderNo(rs.getString(index++));
                order.setPhone(rs.getString(index++));
                order.setAmount(rs.getDouble(index++));
                order.setName(rs.getString(index++));
                order.setTime(rs.getDate(index++));
                result.add(order);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("db connect error.");
        } finally {
            DBConnect.close();
        }
    }

    /**
     * delete order by order no
     */
    public void delete(String orderNo) {
        try {
            String sql = "DELETE FROM " + table_name + " WHERE order_no = '" + orderNo + "'";
            DBConnect.getConnect().createStatement().executeUpdate(sql);
            DBConnect.getConnect().close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("db connect error.");
        } finally {
            DBConnect.close();
        }
    }

    /**
     * update order info
     */
    public void update(Order order) {

        try {
            String sql = "UPDATE " + table_name + " SET name = '" + order.getName() + "', phone = '" + order.getPhone() + "', amount = " + order.getAmount() + " WHERE order_no = '" + order.getOrderNo() + "'";
            DBConnect.getConnect().createStatement().executeUpdate(sql);
            DBConnect.getConnect().close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("db connect error.");
        } finally {
            DBConnect.close();
        }

    }
}
