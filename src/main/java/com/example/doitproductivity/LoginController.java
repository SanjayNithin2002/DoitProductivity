package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class LoginController {

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    void loginToDb(ActionEvent event) {
        boolean flag = false;
        usernameLabel.setText("");
        passwordLabel.setText("");
        boolean usernameValid = Pattern.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", usernameField.getText());
        boolean passwordValid = Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$", passwordField.getText());
        if(!usernameValid)
            usernameLabel.setText("Invalid Username");
        if(!passwordValid)
            passwordLabel.setText("Invalid Password");
        if(usernameValid && passwordValid){
            DatabaseConnection db = new DatabaseConnection();
            Connection connectDb = db.getConnection();
            String connectQuery = String.format("SELECT EXISTS(SELECT * FROM info WHERE username = '%s') ;",usernameField.getText());
            try {
                Statement statement = connectDb.createStatement();
                ResultSet queryOutput = statement.executeQuery(connectQuery);
                queryOutput.next();
                if(queryOutput.getInt(1) == 1){
                    flag = true;
                }
                else{
                    usernameLabel.setText("Invalid Username");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(flag);
            connectQuery = String.format("SELECT password  FROM info WHERE username = '%s' ;",usernameField.getText());
            try {
                Statement statement = connectDb.createStatement();
                ResultSet queryOutput = statement.executeQuery(connectQuery);
                queryOutput.next();
                if(queryOutput.getString(1).matches(passwordField.getText())){
                    flag = true;
                }
                else{
                    flag = false;
                    passwordLabel.setText("Incorrect Password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(flag == true){
                System.out.println("Valid");
                User u = new User();
                String username = usernameField.getText().replace("@","_");
                username = username.replace(".","_");
                u.setUsername(username);
                try{
                    Parent home_page_parent =   FXMLLoader.load(getClass().getResource("add.fxml"));
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene home_page_scene = new Scene(home_page_parent);
                    app_stage.setUserData(u);
                    app_stage.setScene(home_page_scene);
                    app_stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }
    @FXML
    public void signUpToDb(ActionEvent e) throws IOException {
        User u = new User();
        u.setUsername("sanj@gmail.com");
        Parent home_page_parent =   FXMLLoader.load(getClass().getResource("signup.fxml"));
        Stage app_stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene home_page_scene = new Scene(home_page_parent);
        app_stage.setUserData(u);
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
}
