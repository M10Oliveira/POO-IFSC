/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.dao.VeiculoDAO;
import br.com.lavacao.model.database.Database;
import br.com.lavacao.model.database.DatabaseFactory;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroVeiculoController implements Initializable {

    
@FXML
    private Button btAlterar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btInserir;

    @FXML
    private Label lbVeiculoCor;

    @FXML
    private Label lbVeiculoId;

    @FXML
    private Label lbVeiculoMarca;

    @FXML
    private Label lbVeiculoModelo;

    @FXML
    private Label lbVeiculoPlaca;
    
    @FXML
    private Label lbVeiculoObs;
    
    @FXML
    private Label lbMotor;

    @FXML
    private TableColumn<Veiculo, String> tableColumnVeiculoModelo;

    @FXML
    private TableColumn<Veiculo, String> tableColumnVeiculoPlaca;

    @FXML
    private TableView<Veiculo> tableViewVeiculos;



    
    private List<Veiculo> listaVeiculos;
    private ObservableList<Veiculo> observableListVeiculos;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final VeiculoDAO veiculoDAO  = new VeiculoDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veiculoDAO.setConnection(connection);
        carregarTableViewVeiculos();
        
        tableViewVeiculos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVeiculos(newValue));
    }     
    
    public void carregarTableViewVeiculos() {
        tableColumnVeiculoModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tableColumnVeiculoPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        
        listaVeiculos = veiculoDAO.listar();
        
        observableListVeiculos = FXCollections.observableArrayList(listaVeiculos);
        tableViewVeiculos.setItems(observableListVeiculos);
    }
    
    public void selecionarItemTableViewVeiculos(Veiculo veiculo) {
        if (veiculo != null) {
            lbVeiculoId.setText(String.valueOf(veiculo.getId())); 
            lbVeiculoModelo.setText(veiculo.getModelo().getNome());
            lbVeiculoCor.setText(veiculo.getCor().getNome());
            lbVeiculoMarca.setText(veiculo.getModelo().getMarca().getNome());
            lbVeiculoPlaca.setText(veiculo.getPlaca());
            lbVeiculoObs.setText(veiculo.getObservacao());
            double potencia = veiculo.getModelo().getMotor().getPotencia();
            lbMotor.setText(String.format("%.1f", potencia/1000)+" - "+veiculo.getModelo().getMotor().getCombustivel());
            
        } else {
            lbVeiculoId.setText(""); 
            lbVeiculoModelo.setText("");
            lbVeiculoCor.setText("");
            lbVeiculoMarca.setText("");
            lbVeiculoPlaca.setText("");
            lbVeiculoObs.setText("");
            lbMotor.setText("");
        }
        
    }
    
    @FXML
    public void handleBtInserir() throws IOException {
        Veiculo veiculo = new Veiculo();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroVeiculoDialog(veiculo);
        if (btConfirmarClicked) {
            veiculoDAO.inserir(veiculo);
            carregarTableViewVeiculos();
        } 
    }
    
    @FXML 
    public void handleBtAlterar() throws IOException {
        Veiculo veiculo = tableViewVeiculos.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            boolean btConfirmarClicked = showFXMLAnchorPaneCadastroVeiculoDialog(veiculo);
            if (btConfirmarClicked) {
                veiculoDAO.alterar(veiculo);
                carregarTableViewVeiculos();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta opera????o requer a sele????o \nde um Veiculo na tabela ao lado");
            alert.show();
        }
    }
    
    @FXML
    public void handleBtExcluir() throws IOException {
        Veiculo veiculo = tableViewVeiculos.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            veiculoDAO.remover(veiculo);
            carregarTableViewVeiculos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta opera????o requer a sele????o \nde um Veiculo na tabela ao lado");
            alert.show();
        }
    }

    private boolean showFXMLAnchorPaneCadastroVeiculoDialog(Veiculo veiculo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroVeiculoController.class.getResource("../view/FXMLAnchorPaneCadastroVeiculoDialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        
        //cria????o de um est??gio de di??logo (StageDialog)
        Stage dialogStage = new Stage(); 
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("../icon/IFSC_logo_vertical.png")));
        dialogStage.setTitle("Cadastro de Veiculo");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //enviando o obejto veiculo para o controller
        FXMLAnchorPaneCadastroVeiculoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVeiculo(veiculo);
        
        //apresenta o di??logo e aguarda a confirma????o do usu??rio
        dialogStage.showAndWait();
        
        return controller.isBtConfirmarClicked();
    }
    
}
