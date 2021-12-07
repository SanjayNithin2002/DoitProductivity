package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PendingController {

    @FXML
    private Button addButton;

    @FXML
    private Button archivedButton;

    @FXML
    private Button completedButton;

    @FXML
    private Button pendingButton;

    @FXML
    private Button showButton;

    @FXML
    private TableView tableView;

    @FXML
    private User temp = new User();

    @FXML
    public void initialize(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User u = (User) stage.getUserData();
        temp.setUsername(u.getUsername());

    }

    @FXML
    void getArchived(ActionEvent event) {
        initialize(event);
        try{
            Parent home_page_parent =   FXMLLoader.load(getClass().getResource("archived.fxml"));
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene home_page_scene = new Scene(home_page_parent);
            app_stage.setUserData(temp);
            app_stage.setScene(home_page_scene);
            app_stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void getCompleted(ActionEvent event) {
        initialize(event);
        try{
            Parent home_page_parent =   FXMLLoader.load(getClass().getResource("completed.fxml"));
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene home_page_scene = new Scene(home_page_parent);
            app_stage.setUserData(temp);
            app_stage.setScene(home_page_scene);
            app_stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void openAddTask(ActionEvent event) {
        initialize(event);
        try{
            Parent home_page_parent =   FXMLLoader.load(getClass().getResource("add.fxml"));
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene home_page_scene = new Scene(home_page_parent);
            app_stage.setUserData(temp);
            app_stage.setScene(home_page_scene);
            app_stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void getPending(ActionEvent event) {
        ;
    }


    @FXML
    void showPending(ActionEvent event) {
        initialize(event);
        tableView.getColumns().clear();
        tableView.getItems().clear();
        TableColumn<Data, String> column1 = new TableColumn("Task");
        column1.setMinWidth(200);
        column1.setCellValueFactory(new PropertyValueFactory("task"));
        TableColumn<Data, Button> column2 = new TableColumn("Action");
        column2.setCellValueFactory(new PropertyValueFactory("Btn"));
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        DatabaseConnection db = new DatabaseConnection();
        Connection connectDb = db.getConnection();
        String connectQuery = "Select taskname from " + temp.getUsername() + " where status = \"PENDING\"";
        try{
            Statement statement = connectDb.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while(queryOutput.next()){
                Data d = new Data(queryOutput.getString("taskname"),"COMPLETED",temp.getUsername(),"Done");
                tableView.getItems().add(d);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
