package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Data {
    String task;
    String message;
    String username;
    Button btn;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    String buttonName;

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Data(String task, String message, String username, String buttonName) {
        this.btn = new Button();
        btn.setText(buttonName);
        this.username = username;
        this.task = task;
        this.message = message;
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    DatabaseConnection db = new DatabaseConnection();
                    Connection connectDb = db.getConnection();
                    String query = String.format("UPDATE %s SET status = ? WHERE taskname = ?",username);
                    PreparedStatement preparedStmt = connectDb.prepareStatement(query);
                    preparedStmt.setString(1,message);
                    preparedStmt.setString(2,task);
                    preparedStmt.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
