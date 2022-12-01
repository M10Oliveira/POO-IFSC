    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.lavacao.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLVBoxMainAppController implements Initializable {

   @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuItem menuItemCadastroMarca;

    @FXML
    private MenuItem menuItemCadastroModelo;

    @FXML
    private MenuItem menuItemCadastroVeiculo;

    @FXML
    private MenuItem menuItemGraficoVendasPorMes;

    @FXML
    private MenuItem menuItemProcessoMotor;

    @FXML
    private MenuItem menuItemProcessoVenda;

    @FXML
    private MenuItem menuItemRelatorioEstoque;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    public void handleMenuItemCadastroMarca() throws IOException {
        AnchorPane a = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastroMarca.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemCadastroVeiculo() throws IOException {
        AnchorPane a = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastroVeiculo.fxml"));
        anchorPane.getChildren().setAll(a);
    }    
    
    @FXML
    public void handleMenuItemCadastroModelo() throws IOException {
        AnchorPane a = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastroModelo.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
     @FXML
    void handleMenuItemCadastroMotor() throws IOException{
        AnchorPane a = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastroMotor.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
}
