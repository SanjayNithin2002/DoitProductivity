package com.example.doitproductivity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CompletedController {

    @FXML
    private Button addButton;

    @FXML
    private Button archivedButton;

    @FXML
    private Button completedButton;

    @FXML
    private TableView tableView;

    @FXML
    private Button pendingButton;

    @FXML
    private Button showButton;

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
    void getCompleted(ActionEvent event) {
        ;
    }

    @FXML
    void getPending(ActionEvent event) {
        initialize(event);
        try{
            Parent home_page_parent =   FXMLLoader.load(getClass().getResource("pending.fxml"));
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
    void showCompleted(ActionEvent event) {
        initialize(event);
        tableView.getItems().clear();
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
        String connectQuery = "Select taskname from " + temp.getUsername() + " where status = \"COMPLETED\"";
        try{
            Statement statement = connectDb.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while(queryOutput.next()){
                Data d = new Data(queryOutput.getString("taskname"),"ARCHIVED",temp.getUsername(),"Archive");

                tableView.getItems().add(d);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
}
