package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView listView;

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
        listView.getItems().clear();
        listView.getItems().add("Completed List :");
        listView.getItems().add("");
        DatabaseConnection db = new DatabaseConnection();
        Connection connectDb = db.getConnection();
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Button> btns = new ArrayList<Button>();
        String connectQuery = "Select taskname from " + temp.getUsername() + " where status = \"COMPLETED\"";
        try{
            Statement statement = connectDb.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while(queryOutput.next()){
                list.add(queryOutput.getString("taskname"));
            }
            for(int i = 0; i < list.size();i++){
                Button btn = new Button("Archive");
                int finalI = i;
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try{
                            DatabaseConnection db = new DatabaseConnection();
                            Connection connectDb = db.getConnection();
                            String query = "UPDATE " + temp.getUsername() + " SET status = \"ARCHIVED\" WHERE taskname = \"" + list.get(finalI) + "\"";
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
