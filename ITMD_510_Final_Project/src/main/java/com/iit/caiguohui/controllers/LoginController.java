package com.iit.caiguohui.controllers;

import com.iit.caiguohui.model.Role;
import com.iit.caiguohui.model.User;
import com.iit.caiguohui.service.UserService;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passWordField;
    @FXML
    private Button loginBtn;

    @FXML
    private void initialize() {
        loginBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    User user = UserService.getInstance().login(usernameField.getText(), passWordField.getText());
                    System.out.println("login success. " + user.getUid());
                    AnchorPane panel;
                    if (user.getRole() == Role.admin.getId()) {
                        panel = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("views/admin.fxml"));
                    } else {
                        panel = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("views/customer.fxml"));
                    }
                    Scene scene = ((Button) event.getSource()).getScene();
                    scene.setRoot(panel);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }
}
