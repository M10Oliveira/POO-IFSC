/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.dao.ModeloDAO;
import br.com.lavacao.model.dao.MotorDAO;
import br.com.lavacao.model.dao.VeiculoDAO;
import br.com.lavacao.model.database.Database;
import br.com.lavacao.model.database.DatabaseFactory;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Motor;
import br.com.lavacao.model.domain.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matheus.ti
 */
public class FXMLAnchorPaneCadastroMotorController implements Initializable {

    @FXML
    private TableView<Motor> tableViewMotores;
    @FXML
    private TableColumn<Motor, String> tableColumnMotorPotencia;
    @FXML
    private TableColumn<Motor, String> tableColumnMotorCombustivel;
    @FXML
    private Label lbMotorId;
    @FXML
    private Label lbMotorPotencia;
    @FXML
    private Label lbMotorCombustivel;
    @FXML
    private Button btInserir;
    @FXML
    private Button btAlterar;
    @FXML
    private Button btExcluir;
    @FXML
    private Label lbMotorModelo;

    /**
     * Initializes the controller class.
     */
    
    private List<Motor> listaMotores;
    private ObservableList<Motor> observableListMotores;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MotorDAO motorDAO  = new MotorDAO();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        motorDAO.setConnection(connection);
        carregarTableViewMotores();
        
        tableViewMotores.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewMotores(newValue));
    }     
    /*@Override
    public void initialize(URL url, ResourceBundle rb) {
    // TODO
    }   */ 

    @FXML
    private void handleBtInserir(ActionEvent event) throws IOException {
           Motor motor = new Motor();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroMotorDialog(motor);
        if (btConfirmarClicked) {
            motorDAO.inserir(motor);
            carregarTableViewMotores();
        } 
    }

    @FXML
    private void handleBtAlterar(ActionEvent event) throws IOException {
        //Modelo modelo = new Modelo();
        Motor motor = tableViewMotores.getSelectionModel().getSelectedItem();
        //Modelo modelo = motor.getModelo();
        //Modelo modelo1 = modeloDAO.buscar(modelo);
        //Motor motor = new Motor();
        if (motor != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroMotorDialog(motor);
            if (buttonConfirmarClicked) {
                motorDAO.atualizar(motor);
                carregarTableViewMotores();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na Tabela.");
            alert.show();
        }
     
        
    }

    @FXML
    private void handleBtExcluir(ActionEvent event) {
    }
    
    public void carregarTableViewMotores() {
        tableColumnMotorCombustivel.setCellValueFactory(new PropertyValueFactory<>("combustivel"));
        tableColumnMotorPotencia.setCellValueFactory(new PropertyValueFactory<>("potencia"));
        
        listaMotores = motorDAO.listar();
        
        observableListMotores = FXCollections.observableArrayList(listaMotores);
        tableViewMotores.setItems(observableListMotores);
    }
    
    public void selecionarItemTableViewMotores(Motor motor) {
        if (motor != null) {
            lbMotorId.setText(String.valueOf(motor.getModelo().getId())); 
            lbMotorModelo.setText(motor.getModelo().getNome());
            lbMotorPotencia.setText(String.valueOf(motor.getPotencia()));
            lbMotorCombustivel.setText(motor.getCombustivel().getDescricao());
            
        } else {
            lbMotorId.setText(""); 
            lbMotorModelo.setText("");
            lbMotorPotencia.setText("");
            lbMotorCombustivel.setText("");
        }
    }
    
    private boolean showFXMLAnchorPaneCadastroMotorDialog(Motor motor) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroMotorController.class.getResource("../view/FXMLAnchorPaneCadastroMotorDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("../icon/IFSC_logo_vertical.png")));
        dialogStage.setTitle("Cadastro de Motor");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //enviando o obejto categoria para o controller
        FXMLAnchorPaneCadastroMotorDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage); 
        controller.setMotor(motor);
        
        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();
        
        return controller.isBtConfirmarClicked();
    }
}
    