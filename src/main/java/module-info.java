module br.com.gustavoclementediniz.dukemarket.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    requires java.sql;

    
    opens br.com.gustavoclementediniz.dukemarket.javafx.controller to javafx.fxml;
    opens br.com.gustavoclementediniz.dukemarket.javafx to javafx.fxml;
          
    exports br.com.gustavoclementediniz.dukemarket.javafx;
    exports br.com.gustavoclementediniz.dukemarket.javafx.controller;
            
    exports br.com.gustavoclementediniz.dukemarket.javafx.DAO;
    exports br.com.gustavoclementediniz.dukemarket.javafx.model;
    exports connection;
    
    requires mysql.connector.java;
    
    
    
}
