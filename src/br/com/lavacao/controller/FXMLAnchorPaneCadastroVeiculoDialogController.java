/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.dao.CorDAO;
import br.com.lavacao.model.dao.MarcaDAO;
import br.com.lavacao.model.dao.ModeloDAO;
import br.com.lavacao.model.database.Database;
import br.com.lavacao.model.database.DatabaseFactory;
import br.com.lavacao.model.domain.Cor;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Veiculo;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.input.InputMethodEvent;


/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroVeiculoDialogController implements Initializable {


    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<Cor> cbCor;

    @FXML
    private ComboBox<Marca> cbMarca;

    @FXML
    private ComboBox<Modelo> cbModelo;

    @FXML
    private TextField tfObservacoes;

    @FXML
    private TextField tfPlaca;
    
    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Veiculo veiculo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        modeloDAO.setConnection(connection);
        corDAO.setConnection(connection);
        carregarComboBoxMarcas();
        carregarComboBoxCores();
    }       

    public boolean isBtConfirmarClicked() {
        return btConfirmarClicked;
    }
    @FXML
    void handleCbMarca(ActionEvent event) {
       
        cbModelo.getSelectionModel().select(veiculo.getModelo());
        carregarComboBoxModelos();
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

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.tfPlaca.setText(this.veiculo.getPlaca());
        if(veiculo.getModelo() != null){
            cbMarca.getSelectionModel().select(veiculo.getModelo().getMarca());
        }
        this.tfObservacoes.setText(this.veiculo.getObservacao());
        cbCor.getSelectionModel().select(veiculo.getCor());
        cbModelo.getSelectionModel().select(veiculo.getModelo());
    }

    @FXML
    public void handleBtConfirmar() {
        //if (validarEntradaDeDados()) {
        Modelo modelo = new Modelo();
        Cor cor = new Cor();
        veiculo.setModelo(cbModelo.getSelectionModel().getSelectedItem());
        veiculo.setPlaca(tfPlaca.getText());
        veiculo.setCor(cbCor.getSelectionModel().getSelectedItem());
        veiculo.setObservacao(tfObservacoes.getText());
        
        btConfirmarClicked = true;
        dialogStage.close();
        //}
    }
    
    @FXML
    public void handleBtCancelar() {
        dialogStage.close();
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
//        if (this.tfModelo.getText() == null || this.tfModelo.getText().length() == 0) {
//            errorMessage += "Nome inválido.\n";
//        }
//        
//        if (this.tfCpf.getText() == null || this.tfCpf.getText().length() == 0) {
//            errorMessage += "CPF inválido.\n";
//        }
//        
//        if (this.tfTelefone.getText() == null || this.tfTelefone.getText().length() == 0) {
//            errorMessage += "Telefone inválido.\n";
//        }
//        
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
     private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    private final CorDAO corDAO = new CorDAO();
    
    
    private List<Marca> listaMarcas;
    private ObservableList<Marca> observableListMarcas; 
    
    public void carregarComboBoxMarcas() {
        listaMarcas = marcaDAO.listar();
        observableListMarcas = 
                FXCollections.observableArrayList(listaMarcas);
        cbMarca.setItems(observableListMarcas);
    }
    private List<Modelo> listaModelos;
    private ObservableList<Modelo> observableListModelos; 
    
    public void carregarComboBoxModelos() {
        listaModelos = modeloDAO.listarPorMarca(cbMarca.getSelectionModel().getSelectedItem());
        observableListModelos = 
                FXCollections.observableArrayList(listaModelos);
        cbModelo.setItems(observableListModelos);
    }    
    private List<Cor> listaCores;
    private ObservableList<Cor> observableListCores; 
    
    public void carregarComboBoxCores() {
        listaCores = corDAO.listar();
        observableListCores = 
                FXCollections.observableArrayList(listaCores);
        cbCor.setItems(observableListCores);
    }    
    
}
