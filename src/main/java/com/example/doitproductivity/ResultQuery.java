package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

class ResultQuery {
    int id;
    String task;
    String status;
    String username;
    public TextField taskField;
    public Button btn;
    ResultQuery(String username,int id,String task,String status){
        this.id = id;
        this.status = status;
        this.task = task;
        this.username = username;
    }
    public void createNodes(){
        taskField = new TextField();
        taskField.setText(task);
        btn = new Button();
        btn.setText("Done");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    try{
                        DatabaseConnection db = new DatabaseConnection();
                        Connection connectDb = db.getConnection();
                        String query = String.format("UPDATE TABLE %s SET status = 'COMPLETED'  where id = %i",username,id);
                        PreparedStatement preparedStmt = connectDb.prepareStatement(query);
                        preparedStmt.execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
}

