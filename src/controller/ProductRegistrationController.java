package controller;

import dao.GroupDAO;
import dao.ProductDAO;
import dto.GroupDTO;
import dto.ProductDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Group;
import model.Product;
import view.AlertBox;
import view.ImageProcessor;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductRegistrationController implements Initializable {
    @FXML
    private BorderPane ProductRegistrationBorderPane;
    @FXML
    private RadioButton productRadioButton;
    @FXML
    private RadioButton serviceRadioButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField productTextField;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private ComboBox groupComboBox;
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView addGroupImageView;
    @FXML
    private ImageView productImageView;
    @FXML
    private Spinner<Integer> productIdSpinner;
    @FXML
    private HBox editHBox;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button editProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button newProductButton;
    @FXML
    private Label idLabel;
    @FXML
    private Label operationErrorLabel;
    @FXML
    private Label imageErrorLabel;
    @FXML
    private Label productErrorLabel;
    @FXML
    private Label brandErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
    private Label groupErrorLabel;
    @FXML
    private Label priceErrorLabel;


    //    private ProductDTO productDTO;
    private ProductDTO productDTO = new ProductDTO();
    private ProductDAO productDAO = new ProductDAO();
    private GroupDTO groupDTO = new GroupDTO();
    private GroupDAO groupDAO = new GroupDAO();
    private FileChooser fileChooser;
    private File imageFile;
    private Stage stage;
    private Image image;
    private ArrayList<ProductDTO> productList;
    private Product product = new Product();
    private Group group = new Group();
    private SpinnerValueFactory<Integer> spinnerValueFactory;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
    private Map listGroupMap;
    private AlertBox alertBox = new AlertBox();
    private ImageProcessor imageProcessor;

    public ProductRegistrationController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Add img menu
        File logoFile = new File("src/img/carrito-de-comprasx512.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File addGroupFile = new File("src/img/addx64.png");
        Image addGroupImage = new Image(addGroupFile.toURI().toString());
        addGroupImageView.setImage(addGroupImage);

        //Create fileChooser
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
//        fileChooser.setInitialDirectory(new File("C:\\Users");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"));

        defaultOverview();

        productTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> productTextField.setText(newValue.toUpperCase())));
        brandTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> brandTextField.setText(newValue.toUpperCase())));
        descriptionTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> descriptionTextField.setText(newValue.toUpperCase())));
        groupComboBox.getEditor().textProperty().addListener(((observableValue, oldValue, newValue) -> groupComboBox.getEditor().setText(newValue.toUpperCase())));
    }

    public void changeImageButtonOnAction(ActionEvent event) throws IOException {
        stage = (Stage) ProductRegistrationBorderPane.getScene().getWindow();
        imageFile = fileChooser.showOpenDialog(stage);

        if (imageFile != null) {
            image = new Image(imageFile.getAbsoluteFile().toURI().toString(), productImageView.getFitWidth(), productImageView.getFitHeight(), true, true);
            productImageView.setImage(image);
            productImageView.setPreserveRatio(true);
            imageProcessor = new ImageProcessor(imageFile);
        }
    }

    public void addGroupImageViewOnAction(MouseEvent event) {
        groupComboBox.setEditable(true);
        groupComboBox.setValue("");
        groupComboBox.setPromptText("Digite el nombre del grupo...");
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void productRadioButtonOnAction(ActionEvent event) {
        productDTO.setInventoryable("YES");
    }

    public void serviceRadioButtonOnAction(ActionEvent event) {
        productDTO.setInventoryable("NO");
    }

    public void registerButtonOnAction(ActionEvent event){
        if (validateFields()) {
            try {
                if (listGroupMap.get(groupComboBox.getValue())==null) {
                    String nameGroup = groupComboBox.getEditor().getText();
                    groupDTO.setGroupName(nameGroup);
                    groupDAO.create(groupDTO);
                    updateListCombobox();
                }
                productDTO.setImageBytes(imageProcessor.getImageBytes());
                productDTO.setName(productTextField.getText());
                productDTO.setBrand(brandTextField.getText());
                productDTO.setDescription(descriptionTextField.getText());
                productDTO.setGroupId((Integer) listGroupMap.get(groupComboBox.getValue()));
                productDTO.setPrice(Double.parseDouble(priceTextField.getText()));
                if (editHBox.visibleProperty().getValue()) {
                    if (productDAO.update(productDTO)) {
                        defaultSearchView();
                        alertBox.getAlertUpdate().showAndWait();
                    } else {
                        alertBox.getAlertError().showAndWait();
                    }
                } else {
                    if (productDAO.create(productDTO)) {
                        String gloss = productDTO.getName() + " - " + productDTO.getBrand() + " - " + productDTO.getDescription() + ".";
                        defaultOverview();
                        alertBox.getAlertCreate(gloss).showAndWait();
                    } else {
                        alertBox.getAlertError().showAndWait();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void editProductButtonOnAction(ActionEvent event) {
        lockStatusComponents(false);
    }

    public void deleteProductButtonOnAction(ActionEvent event) {
        String productDetail = productDTO.getName() + " - " + productDTO.getBrand() + " - " + productDTO.getDescription() + ".";
        Optional<ButtonType> result = alertBox.getAlertDelete(productDetail).showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                if (productDAO.delete(idTextField.getText())) {
                    alertBox.getAlertInformationDelete().showAndWait();
                    defaultOverview();
                } else {
                    alertBox.getAlertError().showAndWait();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void newProductButtonOnAction(ActionEvent event) throws SQLException, FileNotFoundException {
        defaultOverview();
    }

    public void searchProduct() {
        try {
            productDTO = productList.get(productIdSpinner.getValue() - 1);
            idTextField.setText(String.valueOf(productDTO.getProductId()));
            productTextField.setText(productDTO.getName());
            brandTextField.setText(productDTO.getBrand());
            descriptionTextField.setText(productDTO.getDescription());
            priceTextField.setText(String.valueOf(productDTO.getPrice()));
            imageProcessor = new ImageProcessor(productDTO.getImageBytes());
            productImageView.setImage(imageProcessor.getImage());
            lockStatusComponents(true);
            visibleStatusComponents(true);
            if (groupDAO.read(productDTO.getGroupId()) != null) {
                groupDTO = groupDAO.read(productDTO.getGroupId());
                groupComboBox.setValue(groupDTO.getGroupName());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean validateFields() {
        int errorCounter = 0;
        if (productRadioButton.isSelected() == false && serviceRadioButton.isSelected() == false) {
            operationErrorLabel.setText("Indique si el registro corresponde a un producto o servicio.");
            errorCounter++;
        }
        if (productImageView.getImage() == null) {
            imageErrorLabel.setText("Seleccione una imagen para el producto o servicio.");
            errorCounter++;
        }
        if (productTextField.getText().isEmpty()) {
            productErrorLabel.setText("Ingrese el nombre del producto o servicio.");
            errorCounter++;
        } else {
            productErrorLabel.setText("");
        }

        if (brandTextField.getText().isEmpty()) {
            if (productRadioButton.isSelected()) {
                brandErrorLabel.setText("Ingrese la marca del producto.");
                errorCounter++;
            }
        } else {
            brandErrorLabel.setText("");
        }

        if (descriptionTextField.getText().isEmpty()) {
            descriptionErrorLabel.setText("Ingrese una descripción para el producto o servicio.");
            errorCounter++;
        } else {
            descriptionErrorLabel.setText("");
        }

        if (groupComboBox.getValue().toString().isEmpty()) {
            groupErrorLabel.setText("Seleccione un grupo para el producto o servicio");
            errorCounter++;
        } else {
            groupErrorLabel.setText("");
        }

        if (priceTextField.getText().isEmpty()) {
            priceErrorLabel.setText("Especifique el precio de venta para el producto o servicio.");
            errorCounter++;
        } else {
            priceErrorLabel.setText("");
        }

        try {
            Double.parseDouble(priceTextField.getText());
            priceErrorLabel.setText("");
        } catch (Exception e) {
            priceErrorLabel.setText("Ingrese un formato de número válido.");
            priceTextField.clear();
            errorCounter++;
        }

        if (errorCounter > 0) {
            return false;
        } else {
            return true;
        }
    }

    public void defaultOverview() {
        updateValueFactorySpinner();
        updateListCombobox();
        defaultValueSpinner();
        defaultValueGroupCombobox();
        defaultImageProduct();
        initialsValuesProductRegistration();
        lockStatusComponents(false);
        visibleStatusComponents(false);
    }

    public void defaultSearchView() throws SQLException {
        int valueSpinner = productIdSpinner.getValue();
        updateValueFactorySpinner();
        productIdSpinner.getValueFactory().setValue(valueSpinner);

        updateListCombobox();
        lockStatusComponents(true);
        visibleStatusComponents(true);
    }

    public void defaultValueSpinner() {
        int sizeProductList = this.productList.size();
        productIdSpinner.getValueFactory().setValue(sizeProductList + 1);
    }

    public void defaultValueGroupCombobox() {
        groupComboBox.setValue(groupComboBox.getItems().get(0));
    }

    public void defaultImageProduct() {
        try {
            imageProcessor = new ImageProcessor();
            productImageView.setImage(imageProcessor.getImage());
            productImageView.setPreserveRatio(true);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void initialsValuesProductRegistration() {
        productDTO.setInventoryable("YES");
        productRadioButton.setSelected(true);
        idTextField.clear();
        productTextField.clear();
        brandTextField.clear();
        descriptionTextField.clear();
        groupComboBox.setValue(groupComboBox.getItems().get(0));
        priceTextField.clear();
    }

    public void lockStatusComponents(boolean state) {
        addGroupImageView.setDisable(state);
        addGroupImageView.setVisible(!state);
        productRadioButton.setDisable(state);
        serviceRadioButton.setDisable(state);
        idTextField.setDisable(true);
        idTextField.setPromptText("Asignación automática...");
        productTextField.setDisable(state);
        brandTextField.setDisable(state);
        descriptionTextField.setDisable(state);
        groupComboBox.setDisable(state);
        groupComboBox.setEditable(false);
        priceTextField.setDisable(state);
    }

    public void visibleStatusComponents(boolean state) {
        editHBox.visibleProperty().setValue(state);
    }

    public void updateListCombobox() {
        listGroupMap = group.getGroupMap();
        ObservableList<String> listNameGroup = FXCollections.observableArrayList(listGroupMap.keySet());
        groupComboBox.setItems(listNameGroup);
    }

    public void updateValueFactorySpinner() {
        try {
            productList = product.getProductList();
            int maxValue = productList.size()+1;
            int initialValue = maxValue;

            //llenando Spinner
            spinnerValueFactory = null;
            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxValue, initialValue);
            spinnerValueFactory.setWrapAround(true);
            productIdSpinner.setValueFactory(spinnerValueFactory);
            spinnerValueFactory.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
                if (newValue != maxValue) {
                    searchProduct();
                } else {
                    defaultOverview();
                }
            }));
            productIdSpinner.getStyleClass().add(productIdSpinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
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

    public class SpinnerListener implements ChangeListener<Integer> {
        @Override
        public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
            searchProduct();
        }
    }

}