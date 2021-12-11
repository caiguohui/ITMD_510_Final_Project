package com.iit.caiguohui.service;

import java.util.List;

import com.iit.caiguohui.dao.OrderDao;
import com.iit.caiguohui.dao.UserDao;
import com.iit.caiguohui.model.Order;

public class OrderService {

    private static OrderService _service;

    private OrderService() {

    }

    public static OrderService getInstance() {
        if (_service == null) {
            synchronized (UserDao.class) {
                if (_service == null) {
                    _service = new OrderService();
                }
            }
        }
        return _service;
    }

    /**
     * get order list
     */
    public List<Order> getList(String orderNo, String phone) {
        return OrderDao.getInstance().getList(orderNo, phone);
    }

    /**
     * delete order by order no
     */
    public void delete(String orderNo) {
        OrderDao.getInstance().delete(orderNo);
    }

    /**
     * update order info
     */
    public void update(Order order) {
        OrderDao.getInstance().update(order);
    }
}
