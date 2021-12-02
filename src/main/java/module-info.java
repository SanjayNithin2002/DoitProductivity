module com.example.doitproductivity {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.doitproductivity to javafx.fxml;
    exports com.example.doitproductivity;
}