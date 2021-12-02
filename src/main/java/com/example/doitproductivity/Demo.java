package com.example.doitproductivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class Demo {
    public  static  void main(String args[]) {

        DatabaseConnection db = new DatabaseConnection();
        Connection connectDb = db.getConnection();
        String connectQuery = "SELECT EXISTS(SELECT * FROM info WHERE username = \"sanjay@gmail.com\") as id;";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            queryOutput.next();
            System.out.println(queryOutput.getString(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
