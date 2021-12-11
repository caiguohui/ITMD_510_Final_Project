package com.iit.caiguohui.controllers;

import java.util.Date;
import java.util.Optional;

import com.iit.caiguohui.model.Order;
import com.iit.caiguohui.service.OrderService;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AdminController {

    @FXML
    private TextField orderNoField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField orderNoUpdateField;
    @FXML
    private TextField nameUpdateField;
    @FXML
    private TextField phoneUpdateField;
    @FXML
    private TextField amountUpdateField;
    @FXML
    private Button searchBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, String> orderNoColumn;
    @FXML
    private TableColumn<Order, String> nameColumn;
    @FXML
    private TableColumn<Order, String> phoneColumn;
    @FXML
    private TableColumn<Order, Double> amountColumn;
    @FXML
    private TableColumn<Order, Date> timeColumn;

    @FXML
    private void initialize() {
        orderNoColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNo"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("phone"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("amount"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Order, Date>("time"));
        tableView.setItems(FXCollections.observableArrayList(OrderService.getInstance().getList(orderNoField.getText(), phoneField.getText())));
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Order order = tableView.getSelectionModel().getSelectedItem();
                if (order != null) {
                    System.out.println("click item data");
                    orderNoUpdateField.setText(order.getOrderNo());
                    nameUpdateField.setText(order.getName());
                    amountUpdateField.setText(String.valueOf(order.getAmount()));
                    phoneUpdateField.setText(order.getPhone());
                }
            }
        });

        searchBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("search...");
                tableView.setItems(FXCollections.observableArrayList(OrderService.getInstance().getList(orderNoField.getText(), phoneField.getText())));
            }
        });
        updateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Order order = tableView.getSelectionModel().getSelectedItem();
                if (order != null) {
                    System.out.println("update...");
                    order.setName(nameUpdateField.getText());
                    order.setAmount(Double.valueOf(amountUpdateField.getText()));
                    order.setPhone(phoneUpdateField.getText());
                    OrderService.getInstance().update(order);
                    clearSelectData();
                    tableView.setItems(FXCollections.observableArrayList(OrderService.getInstance().getList(orderNoField.getText(), phoneField.getText())));
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Data has been updated");
                    alert.showAndWait();
                }
            }
        });
        deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Order order = tableView.getSelectionModel().getSelectedItem();
                if (order != null) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setContentText("Are you sure you want to delete the record?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        System.out.println("delete...");
                        OrderService.getInstance().delete(order.getOrderNo());
                        clearSelectData();
                        tableView.setItems(FXCollections.observableArrayList(OrderService.getInstance().getList(orderNoField.getText(), phoneField.getText())));
                    }
                }
            }
        });
    }

    /**
     * clear select data
     */
    private void clearSelectData() {
        orderNoUpdateField.setText("");
        nameUpdateField.setText("");
        amountUpdateField.setText("");
        phoneUpdateField.setText("");
    }
}
