package com.example.doitproductivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class SignupController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    public  void initialize(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User u = (User) stage.getUserData();
        System.out.println(u.getUsername());
    }
    @FXML
    void createUser(ActionEvent event) {
        boolean flag = true;
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
           String connectQuery = String.format("SELECT EXISTS(SELECT * FROM info WHERE username = '%s') as id;",usernameField.getText());
           try {
               Statement statement = connectDb.createStatement();
               ResultSet queryOutput = statement.executeQuery(connectQuery);
               queryOutput.next();
               if(queryOutput.getInt(1) == 1){
                   usernameLabel.setText("Username already exists");
                   flag = false;
               };

           } catch (Exception e) {
               e.printStackTrace();
           }
           if(flag == true){
               String queryString = String.format("insert into info values('%s','%s')",usernameField.getText(),passwordField.getText());
               try{
                   PreparedStatement preparedStatement = connectDb.prepareStatement(queryString);
                   preparedStatement.execute();
               }catch (Exception e){
                   e.printStackTrace();
               }
               String username = usernameField.getText().replace("@","_");
               username = username.replace(".","_");
               String query = String.format("create table %s (id int primary key auto_increment, taskname varchar(200), status varchar(30))",username);
               try{
                   PreparedStatement preparedStmt = connectDb.prepareStatement(query);
                   preparedStmt.execute();
                   connectDb.close();
               }catch (Exception e) {
                   e.printStackTrace();
               }
               System.out.println("Valid");
               User u = new User();
               u.setUsername(username);
               try{
                   Parent home_page_parent =   FXMLLoader.load(getClass().getResource("add.fxml"));
                   Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                   Scene home_page_scene = new Scene(home_page_parent);
                   app_stage.setScene(home_page_scene);
                   app_stage.setUserData(u);
                   app_stage.show();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
        }
    }
}
