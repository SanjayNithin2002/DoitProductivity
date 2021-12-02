package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private ListView listView;

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
        listView.getItems().clear();
        listView.getItems().add("Pending List :");
        listView.getItems().add("");
        initialize(event);
        DatabaseConnection db = new DatabaseConnection();
        Connection connectDb = db.getConnection();
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Button> btns = new ArrayList<Button>();
        String connectQuery = "Select taskname from " + temp.getUsername() + " where status = \"PENDING\"";
        try{
            Statement statement = connectDb.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while(queryOutput.next()){
                list.add(queryOutput.getString("taskname"));
            }
            for(int i = 0; i < list.size();i++){
                Button btn = new Button("Done");
                int finalI = i;
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try{
                            DatabaseConnection db = new DatabaseConnection();
                            Connection connectDb = db.getConnection();
                            String query = "UPDATE " + temp.getUsername() + " SET status = \"COMPLETED\" WHERE taskname = \"" + list.get(finalI) + "\"";
                            System.out.println(query);
                            PreparedStatement preparedStmt = connectDb.prepareStatement(query);
                            preparedStmt.execute();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                btns.add(btn);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        for(int i = 0;i< list.size();i++){
            listView.getItems().add(list.get(i));
            listView.getItems().add(btns.get(i));
        }
    }

}
