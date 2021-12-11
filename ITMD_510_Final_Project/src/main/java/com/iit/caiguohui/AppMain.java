package com.iit.caiguohui;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.kordamp.bootstrapfx.BootstrapFX;

import com.iit.caiguohui.dao.OrderDao;
import com.iit.caiguohui.dao.UserDao;
import com.iit.caiguohui.model.Order;
import com.iit.caiguohui.model.User;
import com.iit.caiguohui.util.ExcelUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppMain extends Application {
    
    /**
     * init system config data
     */
    public void initData() {
        // init user data
        {
            URL fileURL = AppMain.class.getClassLoader().getResource("users.csv");
            if (fileURL == null) {
                throw new RuntimeException("not found file.");
            }
            List<List<String>> userData = ExcelUtil.readData(new File(fileURL.getFile()));
            List<User> users = new ArrayList<>();
            for (List<String> item : userData) {
                if (item.isEmpty() || item.size() != 3) {
                    continue;
                }
                User user = new User();
                user.setUsername(item.get(0));
                user.setPassword(DigestUtils.md5Hex(item.get(1)));
                user.setRole(Integer.valueOf(item.get(2)));
                users.add(user);
            }
            UserDao.getInstance().createTable();
            if (!users.isEmpty()) {
                UserDao.getInstance().insert(users);
            }
        }

        // init order data
        {
            URL fileURL = AppMain.class.getClassLoader().getResource("orders.csv");
            if (fileURL == null) {
                throw new RuntimeException("not found file.");
            }
            List<List<String>> dataList = ExcelUtil.readData(new File(fileURL.getFile()));
            List<Order> orders = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (List<String> item : dataList) {
                if (item.isEmpty() || item.size() != 5) {
                    continue;
                }
                try {
                    Order order = new Order();
                    order.setOrderNo(item.get(0));
                    order.setName(item.get(1));
                    order.setPhone(item.get(2));
                    order.setAmount(Double.valueOf(item.get(3)));
                    order.setTime(sdf.parse(item.get(4)));
                    orders.add(order);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            OrderDao.getInstance().createTable();
            if (!orders.isEmpty()) {
                OrderDao.getInstance().insert(orders);
            }
        }
    }

    @Override
    public void start(Stage stage) {
        initData();
        AnchorPane panel;
        try {
            panel = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("views/login.fxml"));
        } catch (Exception e) {
            throw new IllegalArgumentException("load login view error.", e);
        }
        Scene scene = new Scene(panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("E-commerce Order Management System");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
