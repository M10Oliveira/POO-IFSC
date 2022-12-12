/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.dao.ModeloDAO;
import br.com.lavacao.model.database.Database;
import br.com.lavacao.model.database.DatabaseFactory;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Motor;
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
 * @author mpisching
 */
public class FXMLAnchorPaneCadastroModeloController implements Initializable {

    @FXML
    private TableView<Modelo> tableView;

    @FXML
    private TableColumn<Modelo, String> tableColumnNome;

    @FXML
    private TableColumn<Modelo, String> tableColumnMarca;

    @FXML
    private Button btAlterar;

    @FXML
    private Button btInserir;

    @FXML
    private Button btRemover;

    @FXML
    private Label lbModeloId;

    @FXML
    private Label lbModeloMarca;

    @FXML
    private Label lbModeloNome;
    
    @FXML
    private Label lbCategoria;



    private List<Modelo> listaModelos;
    private ObservableList<Modelo> observableListModelos;

    //acesso ao banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeloDAO.setConnection(connection);

        carregarTableView();

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));

    }

    public void carregarTableView() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        
        listaModelos = modeloDAO.listar();
        
        observableListModelos = FXCollections.observableArrayList(listaModelos);
        tableView.setItems(observableListModelos);
    }
    
    public void selecionarItemTableView(Modelo modelo) {
        if (modelo != null) {
            lbModeloId.setText(Integer.toString(modelo.getId()));
            lbModeloNome.setText(modelo.getNome());
            lbModeloMarca.setText(modelo.getMarca().getNome());
            lbCategoria.setText(modelo.getCategoria().toString());
           
        } else {
            lbModeloId.setText("");
            lbModeloNome.setText("");
            lbModeloMarca.setText("");
            lbCategoria.setText("");
        }
    }
    

    @FXML
    public void handleBtInserir() throws IOException {
        Modelo modelo = new Modelo();
        //Motor motor = new Motor();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosModelosDialog(modelo);
        if (buttonConfirmarClicked) {
            modeloDAO.inserir(modelo);
            carregarTableView();
        }
    }
    
    @FXML
    public void handleBtAlterar() throws IOException {
        Modelo modelo = tableView.getSelectionModel().getSelectedItem();
        modelo = modeloDAO.buscar(modelo);
        if (modelo != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosModelosDialog(modelo);
            if (buttonConfirmarClicked) {
                modeloDAO.alterar(modelo);
                carregarTableView();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na Tabela.");
            alert.show();
        }
    }
    
    @FXML
    public void handleBtRemover() throws IOException {
        Modelo modelo = tableView.getSelectionModel().getSelectedItem();
        if (modelo != null) {
            modeloDAO.remover(modelo);
            carregarTableView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na Tabela.");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosModelosDialog(Modelo modelo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroModeloDialogController.class.getResource( 
            "../view/FXMLAnchorPaneCadastroModeloDialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        
        //criando um estágio de diálogo  (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("../icon/IFSC_logo_vertical.png")));
        dialogStage.setTitle("Cadastro de modelos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //Setando o modelo ao controller
        FXMLAnchorPaneCadastroModeloDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setModelo(modelo);
        
        dialogStage.showAndWait();
        
        return controller.isButtonConfirmarClicked();
    }


}
