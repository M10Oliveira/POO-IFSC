/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.dao.ModeloDAO;
import br.com.lavacao.model.dao.MotorDAO;
import br.com.lavacao.model.domain.ECombustivel;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Motor;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroMotorDialogController implements Initializable {
    
    @FXML
    private ChoiceBox<ECombustivel> cbCombustivel;

    @FXML
    private ChoiceBox<Modelo> cbModelo;
    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfPotencia;
    
    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Motor motor;
    MotorDAO motorDAO = new MotorDAO();
    ModeloDAO modeloDAO = new ModeloDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxCombust();
        carregarComboBoxModelos();
    }       

    public boolean isBtConfirmarClicked() {
        return btConfirmarClicked;
    }

    public void setBtConfirmarClicked(boolean btConfirmarClicked) {
        this.btConfirmarClicked = btConfirmarClicked;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
        if(motor.getPotencia() != 0){
            tfPotencia.setText(String.valueOf(motor.getPotencia()));
            cbCombustivel.getSelectionModel().select(motor.getCombustivel());
            cbModelo.getSelectionModel().select(motor.getModelo());
        }
    }
    

    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
          
            btConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleBtCancelar() {
        dialogStage.close();
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        /*if (this.tfDescricao.getText() == null || this.tfDescricao.getText().length() == 0) {
        errorMessage += "Descrição inválida.\n";
        }*/
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //exibindo uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Corrija os campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    private List<ECombustivel> listaCombust;
    private ObservableList<ECombustivel> observableListCombust; 
    public void carregarComboBoxCombust() {
        listaCombust = motorDAO.listarCombustivel();
        observableListCombust = 
                FXCollections.observableArrayList(listaCombust);
        cbCombustivel.setItems(observableListCombust);
    }   
    
    private List<Modelo> listaModelos;
    private ObservableList<Modelo> observableListModelos; 
    
    public void carregarComboBoxModelos() {
        listaModelos = modeloDAO.listarTudo();
        observableListModelos = 
                FXCollections.observableArrayList(listaModelos);
        cbModelo.setItems(observableListModelos);
    }
    
}
