package controller;

import dao.ProductDAO;
import dto.ProductDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductListController implements Initializable {
    @FXML
    private TableView<ProductDTO> productTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTableView();
    }

    public void updateTableView() {
        try {
            Product product = new Product();
            ArrayList<ProductDTO> productList = product.getProductList();
            ObservableList<ProductDTO> products = FXCollections.observableArrayList(productList);
            productTableView.setItems(products);
//            columnIdProduct.setMinWidth(productTableView.getMaxWidth()/4);
            TableColumn<ProductDTO, String> columnProductId = new TableColumn<>("ID");
            columnProductId.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("productId"));

            TableColumn<ProductDTO, String> columnProductName = new TableColumn<>("NOMBRE");
            columnProductName.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("name"));

            TableColumn<ProductDTO, String> columnProductBrand = new TableColumn<>("MARCA");
            columnProductBrand.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("brand"));

            TableColumn<ProductDTO, String> columnProductDescription = new TableColumn<>("DESCRIPCION");
            columnProductDescription.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("description"));

            TableColumn<ProductDTO, Integer> columnProductGroup = new TableColumn<>("GRUPO");
            columnProductGroup.setCellValueFactory(new PropertyValueFactory<ProductDTO, Integer>("groupId"));

            TableColumn<ProductDTO, Double> columnProductPrice = new TableColumn<>("PRECIO DE VENTA");
            columnProductPrice.setCellValueFactory(new PropertyValueFactory<ProductDTO, Double>("price"));

            productTableView.getColumns().addAll(columnProductId, columnProductName, columnProductBrand, columnProductDescription, columnProductGroup, columnProductPrice);

            productTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void min(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
