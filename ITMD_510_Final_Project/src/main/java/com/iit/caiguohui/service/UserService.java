package com.iit.caiguohui.service;

import org.apache.commons.codec.digest.DigestUtils;

import com.iit.caiguohui.dao.UserDao;
import com.iit.caiguohui.model.User;

public class UserService {

    private static UserService _service;

    private UserService() {

    }

    public static UserService getInstance() {
        if (_service == null) {
            synchronized (UserDao.class) {
                if (_service == null) {
                    _service = new UserService();
                }
            }
        }
        return _service;
    }

    /**
     * log in system
     * 
     * @param username
     * @param password
     * @return Return user information after success
     */
    public User login(String username, String password) {
        User user = UserDao.getInstance().getByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Login failed. not found user name");
        }
        if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
            throw new IllegalArgumentException("Login failed, user name or password is wrong");
        }
        return user;
    }
}
