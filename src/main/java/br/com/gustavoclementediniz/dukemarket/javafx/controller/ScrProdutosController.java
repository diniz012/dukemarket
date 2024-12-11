/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.gustavoclementediniz.dukemarket.javafx.controller;

import br.com.gustavoclementediniz.dukemarket.javafx.DAO.ProdutoDAO;
import br.com.gustavoclementediniz.dukemarket.javafx.model.Produto;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * ScrProdutoController Class
 *
 * @author gcd
 */
public class ScrProdutosController implements Initializable {

    /**
     * Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Vincula as colunas da TableView a classe Produto
        bindColumns();

        // Busca Dados
        carregaDados();
    }

    @FXML
    TableView<Produto> tblProduto;

    @FXML
    TableColumn<Produto, Integer> tcoId;

    @FXML
    TableColumn<Produto, String> tcoCodBarras;

    @FXML
    TableColumn<Produto, String> tcoDescricao;

    @FXML
    TableColumn<Produto, Double> tcoQtd;

    @FXML
    TableColumn<Produto, Double> tcoValorCompra;

    @FXML
    TableColumn<Produto, Double> tcoValorVenda;

    @FXML
    TableColumn<Produto, Calendar> tcoDataCadastro;

    @FXML
    TextField txtId;

    @FXML
    TextField txtCodBarras;

    @FXML
    TextField txtDescricao;

    @FXML
    TextField txtQtd;

    @FXML
    TextField txtValorCompra;

    @FXML
    TextField txtValorVenda;

    @FXML
    DatePicker dtpDataCadastro;

    ProdutoDAO pDAO;
    Produto pClicked; // Armazenar o produto clicando na tabela
    private boolean flagNovo;

    /**
     *
     * Conecta as clunas da TableView com os atributos da classe Produto
     *
     * @return void
     */
    private void bindColumns() {

        // Vincula a coluna tcoId com o atributo id da classe Produtos
        tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Adicona uma configuracao extra de alinhamento a direita.
        tcoId.setStyle("-fx- alignment: CENTER_RIGHT;");

        tcoCodBarras.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
        tcoCodBarras.setStyle("-fx-alignment: CENTER_RIGHT;");

        tcoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tcoQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tcoQtd.setStyle("-fx-alignment: CENTER_RIGHT;");

        tcoValorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
        tcoValorCompra.setStyle("-fx-alignment: CENTER_RIGHT;");

        tcoValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        tcoValorVenda.setStyle("-fx-alignment: CENTER_RIGHT;");

        tcoDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        tcoDataCadastro.setStyle("-fx-alignment: CENTER_RIGHT:");

    }

    /**
     *
     * Carrega os dados do Produtos que estao na base e vincula a Tableview.
     *
     */
    public void carregaDados() {

        this.pDAO = new ProdutoDAO();

        // Carrega view tblProduto com o todo os resultados de produto.
        this.tblProduto.getItems().setAll(pDAO.getResults());

    }

    /**
     * Action do botao btnNovo Limpa os campos e posiciona o focus em txtId
     *
     * @return void
     */
    @FXML
    private void btnNovoClick() {
        this.flagNovo = true;
        txtId.setText("auto");
        txtId.setEditable(false);

        txtCodBarras.setText("");
        txtQtd.setText("");
        txtValorVenda.setText("");
        txtValorCompra.setText("");
        dtpDataCadastro.setValue(null);
        
    }

    /**
     * Gerencia o click na tabela, identificando qual registro foi selecionado e
     * preenchendo os TextFields.
     *
     */
    @FXML
    public void tblProdutosOnMouseClicked() {

        // getSelectedItem devolve um objeto Produto.
        // pClicked representa o Produto Clicado
        
        this.flagNovo = false;
        this.pClicked = tblProduto.getSelectionModel().getSelectedItem();

        if (pClicked != null) {
            txtId.setText(String.valueOf(pClicked.getId()));
            txtId.setEditable(false);

            txtCodBarras.setText(pClicked.getCodBarras());
            txtDescricao.setText(pClicked.getDescricao());

            txtQtd.setText(String.valueOf(pClicked.getQtd()));
            txtValorCompra.setText(String.valueOf(pClicked.getValorCompra()));
            txtValorVenda.setText(String.valueOf(pClicked.getValorVenda()));

            // Tratamento da data do cadastro:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            formatter = formatter.withLocale(Locale.US);
            LocalDate date = LocalDate.parse(pClicked.getDataCadastro(), formatter);
            dtpDataCadastro.setValue(date);
            dtpDataCadastro.setEditable(false);

            this.flagNovo = false;
        }
    }

    /**
     * Gerencia o click do botao Excluir. Remova o registro seleciona -
     * carregando em pClicked.
     */
    @FXML
    public void btnExcluirClick() {

        if (this.pClicked != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirma Exclusão de Produto?");
            alert.setHeaderText(pClicked.getDescricao());
            alert.setContentText("Tem certeza que deseja excluir ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.pDAO.delete(this.pClicked.getId());
                this.tblProduto.getItems().remove(this.pClicked);

            }

            this.tblProduto.getSelectionModel().clearSelection();
            pClicked = null;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ops!");
            alert.setHeaderText("Atenção");
            alert.setContentText("Voce deve selecionar um registro antes de excluí-lo!");

            alert.showAndWait();

        }

    }

    /**
     * Gerencia o click do Salvar. Adiciona um novo registro quando flagNovo ==
     * true ou atualiza quando flagNovo == false;
     *
     */
    @FXML
    public void btnSalvarClick() throws SQLException {

        Produto p = new Produto();

        p.setCodBarras(txtCodBarras.getText());
        p.setDescricao(txtDescricao.getText());
        p.setQtd(Double.parseDouble(txtQtd.getText()));
        p.setValorCompra(Double.parseDouble(txtValorCompra.getText()));
        p.setValorVenda(Double.parseDouble(txtValorVenda.getText()));

        if (this.flagNovo) {
            // Novo produto.
            this.pDAO.create(p);

        } else {
            // Ops.. é uma atualizaçao!!
            p.setId(Integer.parseInt(txtId.getText()));
            this.pDAO.update(p);
        }
        this.carregaDados();

    }

}
