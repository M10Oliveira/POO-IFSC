/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.dao.MarcaDAO;
import br.com.lavacao.model.dao.ModeloDAO;
import br.com.lavacao.model.dao.MotorDAO;
import br.com.lavacao.model.database.Database;
import br.com.lavacao.model.database.DatabaseFactory;
import br.com.lavacao.model.domain.ECategoria;
import br.com.lavacao.model.domain.ECombustivel;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Motor;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mpisching
 */
public class FXMLAnchorPaneCadastroModeloDialogController implements Initializable {


    
    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<Marca> cbMarca;

    @FXML
    private Label labelModeloMarca;

    @FXML
    private Label labelModeloNome;

    @FXML
    private TextField tfNome;
    
    @FXML
    private Label labelModeloMotor;
    
    @FXML
    private ChoiceBox<ECombustivel> cbCombustivel;
    
    @FXML
    private TextField tfPotencia;
    

    @FXML
    private Label labelCombustivel;
    
    @FXML
    private ChoiceBox<ECategoria> cbCategoria;
    
    @FXML
    private Label labelCategoriaModelo;
    
//    private List<Marca> listaMarcas;
//    private ObservableList<Marca> observableListMarcas;
        
    //atributos para manipulação de banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    private final MotorDAO motorDAO = new MotorDAO();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    
    
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Modelo modelo;  
    private Motor motor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        carregarComboBoxMarcas();
        setFocusLostHandle();
        carregarComboBoxCombust();
        carregarComboBoxCategorias();
        cbCategoria.setValue(ECategoria.PEQUENO);
        cbCombustivel.setValue(ECombustivel.GASOLINA);
    } 
    
    private void setFocusLostHandle() {
        tfNome.focusedProperty().addListener((ov, oldV, newV) -> {
        if (!newV) { // focus lost
                if (tfNome.getText() == null || tfNome.getText().isEmpty()) {
                    //System.out.println("teste focus lost");
                    tfNome.requestFocus();
                }
            }
        });
    }
    
    private List<Marca> listaMarcas;
    private ObservableList<Marca> observableListMarcas; 
    
    public void carregarComboBoxMarcas() {
        listaMarcas = marcaDAO.listar();
        observableListMarcas = 
                FXCollections.observableArrayList(listaMarcas);
        cbMarca.setItems(observableListMarcas);
    } 
    private List<ECombustivel> listaCombust;
    private ObservableList<ECombustivel> observableListCombust; 
    
    public void carregarComboBoxCombust() {
        listaCombust = motorDAO.listarCombustivel();
        observableListCombust = 
                FXCollections.observableArrayList(listaCombust);
        cbCombustivel.setItems(observableListCombust);
    }    
    
    private List<ECategoria> listaCategorias;
    private ObservableList<ECategoria> observableListCategorias; 
    
    public void carregarComboBoxCategorias() {
        listaCategorias = modeloDAO.listarCategorias();
        observableListCategorias = 
                FXCollections.observableArrayList(listaCategorias);
        cbCategoria.setItems(observableListCategorias);
    }    
    
    /**
     * @return the dialogStage
     */
    public Stage getDialogStage() {
        return dialogStage;
    }

    /**
     * @param dialogStage the dialogStage to set
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * @return the buttonConfirmarClicked
     */
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    /**
     * @param buttonConfirmarClicked the buttonConfirmarClicked to set
     */
    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
        tfNome.setText(modelo.getNome());
        if(modelo.getMotor().getPotencia() != 0 && modelo.getMotor().getCombustivel() != null) {
            tfPotencia.setText(String.valueOf(modelo.getMotor().getPotencia()));
            cbCombustivel.getSelectionModel().select(modelo.getMotor().getCombustivel());
            cbCategoria.getSelectionModel().select(modelo.getCategoria());
        } 
        else{
            
        //tfPotencia.setText("teste");
        //cbCombustivel.getSelectionModel().select(modelo.getMotor().getCombustivel());
        cbMarca.getSelectionModel().select(modelo.getMarca());
        }
    }    
    
    @FXML
    private void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            modelo.setNome(tfNome.getText());
           
            modelo.setMarca(
                    cbMarca.getSelectionModel().getSelectedItem());
            modelo.getMotor().setPotencia(Integer.parseInt((tfPotencia.getText())));
            modelo.getMotor().setCombustivel(cbCombustivel.getValue());
            //modelo.getMotor().setModelo(modelo);
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleBtCancelar() {
        dialogStage.close();
    }
    
        //validar entrada de dados do cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (tfNome.getText() == null || tfNome.getText().isEmpty()) {
            errorMessage += "Nome inválido!\n";
        }
        
        if (cbMarca.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione uma marca!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campo(s) inválido(s), por favor corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
   
}
