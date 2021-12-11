package com.iit.caiguohui.controllers;

import java.util.Date;

import com.iit.caiguohui.model.Order;
import com.iit.caiguohui.service.OrderService;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CustomerController {

    @FXML
    private TextField orderNoField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button searchBtn;
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
        searchBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("search...");
                tableView.setItems(FXCollections.observableArrayList(OrderService.getInstance().getList(orderNoField.getText(), phoneField.getText())));
            }
        });
    }
}
