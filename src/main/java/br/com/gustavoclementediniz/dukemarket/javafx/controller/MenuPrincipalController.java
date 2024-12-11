/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.gustavoclementediniz.dukemarket.javafx.controller;

import br.com.gustavoclementediniz.dukemarket.javafx.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author qualifica
 */
public class MenuPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void btnProdutosClick() throws IOException {
        
        try {
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(App.class.getResource("scrProdutos.fxml"));
          /*
           * if "fx:controller" is not set in fxml
           * fxmlLoader.setLocation(NewWindowController);
           */
          Scene scene = new Scene (fxmlLoader.load(), 600, 450);
          Stage stage = new Stage();
          stage.setTitle("CRUD Produtos");
          stage.setScene(scene);
          stage.show();
        
        } catch (IOException e){
             Logger logger = Logger.getLogger(getClass().getName());
             logger.log(Level.SEVERE,"Failed to create new Window scrProdutos." , e); 
   
   
        }
    }
}

   
        
    


