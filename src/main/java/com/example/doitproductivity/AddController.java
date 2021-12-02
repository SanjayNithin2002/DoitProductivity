package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddController {

    @FXML
    private Button addButton;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button archivedButton;

    @FXML
    private Button completedButton;

    @FXML
    private Button pendingButton;

    @FXML
    private TextField taskname;

    @FXML
    private User temp = new User();

    @FXML
    public void initialize(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User u = (User) stage.getUserData();
        temp.setUsername(u.getUsername());
        System.out.println(u.getUsername());
    }

    @FXML
    void addTask(ActionEvent event) {
        initialize(event);
        DatabaseConnection db = new DatabaseConnection();
        Connection connectDb = db.getConnection();
        String queryString = String.format("insert into %s (taskname,status) values(?,?);",temp.getUsername());
        try{
            PreparedStatement preparedStatement = connectDb.prepareStatement(queryString);
            preparedStatement.setString(1,taskname.getText());
            preparedStatement.setString(2,"PENDING");
            preparedStatement.execute();
            connectDb.close();
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
        ;
    }

}
