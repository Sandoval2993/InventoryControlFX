package controller;

import dao.GroupDAO;
import dao.ProductDAO;
import dto.GroupDTO;
import dto.ProductDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Group;
import model.Products;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.Dialog;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
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
    private FileInputStream fileInputStream;
    private File imageFile;
    private Stage stage;
    private Image image;
    private String inventoryable;
    private int newId;
    private Products products = new Products();
    private Group group = new Group();
    private ObservableList idProductsList;
    private SpinnerValueFactory<Integer> spinnerValueFactory;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images","*.png","*.jpg","*.gif"));

        //Set values default
        productDTO.setInventoryable("YES");
        groupComboBox.setEditable(false);

        lockStatusComponents(false);
        visibleStatusComponents(false);

        setUpdatedValueFactoryIdSpinner();
        setUpdatedListCombobox();

        alert.initStyle(StageStyle.TRANSPARENT);
        alert2.initStyle(StageStyle.TRANSPARENT);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("values/style.css");
        dialogPane.getStyleClass().add("myDialog");
        dialogPane.getStyleClass().add("borderPane");

        DialogPane dialogPane2 = alert.getDialogPane();
        dialogPane2.getStylesheets().add("values/style.css");
        dialogPane2.getStyleClass().add("myDialog");
        dialogPane.getStyleClass().add("borderPane");

        try {
            defaultOverview();
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }

        productTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> productTextField.setText(newValue.toUpperCase())));
        brandTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> brandTextField.setText(newValue.toUpperCase())));
        descriptionTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> descriptionTextField.setText(newValue.toUpperCase())));
        groupComboBox.getEditor().textProperty().addListener(((observableValue, oldValue, newValue)-> groupComboBox.getEditor().setText(newValue.toUpperCase())));
    }

    public void changeImageButtonOnAction(ActionEvent event) throws FileNotFoundException {
        stage = (Stage) ProductRegistrationBorderPane.getScene().getWindow();
        imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile!=null){
//            System.out.println(file.getAbsolutePath());
            image = new Image(imageFile.getAbsoluteFile().toURI().toString(), productImageView.getFitWidth(), productImageView.getFitHeight(), true, true);
            productImageView.setImage(image);
            productImageView.setPreserveRatio(true);
        }else{
//            System.out.println(productDTO.getImage());
            imageFile = new File("src/img/addImage-x512.png");
        }
        productDTO.setImage(new FileInputStream(imageFile));
    }

    public void addGroupImageViewOnAction(MouseEvent event){
        groupComboBox.setEditable(true);
        groupComboBox.setValue("");
        groupComboBox.setPromptText("Digite el nombre del grupo...");
    }

    public void cancelButtonOnAction(ActionEvent event){ Stage stage = (Stage) cancelButton.getScene().getWindow(); stage.close(); }

    public void productRadioButtonOnAction(ActionEvent event){
        productDTO.setInventoryable("YES");
    }

    public void serviceRadioButtonOnAction(ActionEvent event){
        productDTO.setInventoryable("NO");
    }

    public void registerButtonOnAction(ActionEvent event) throws SQLException, IOException {
        if (validateFields()) {
            try {
                int groupId = group.searchIdGroup(groupComboBox.getValue().toString());
                if (groupId == 0) {
                    String nameGroup = groupComboBox.getEditor().getText();
                    groupDTO.setGroupName(nameGroup);
                    groupDAO.create(groupDTO);
                    groupId = group.searchIdGroup(nameGroup);
                }
                productDTO.setProductId(productIdSpinner.getValue());
                productDTO.setName(productTextField.getText());
                productDTO.setBrand(brandTextField.getText());
                productDTO.setDescription(descriptionTextField.getText());
                productDTO.setGroupId(groupId);
                productDTO.setPrice(Double.parseDouble(priceTextField.getText()));
                if (editHBox.visibleProperty().getValue()) {
                    if(productDAO.update(productDTO)) {
                        defaultSearchView();
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Actualización");
                        alert.setHeaderText("Actualización exitosa");
                        alert.setContentText("Los datos fueron actualizados satisfactoriamente.");
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.showAndWait();
                    }else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Actualización no realizada");
                        alert.showAndWait();
                    }
                } else {
                    if(productDAO.create(productDTO)) {
                        setUpdatedValueFactoryIdSpinner();
                        setUpdatedListCombobox();
                        defaultOverview();
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Registro");
                        alert.setHeaderText("Registro exitoso");
                        alert.setContentText("Se realizó el registro de " + productDTO.getName() + " - " + productDTO.getBrand() + " - " + productDTO.getDescription() + ".");
                        alert.showAndWait();
                    }else{
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Registro no realizado");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void editProductButtonOnAction(ActionEvent event){
        lockStatusComponents(false);
    }

    public void deleteProductButtonOnAction(ActionEvent event) throws SQLException, FileNotFoundException {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminación");
        alert.setHeaderText("Eliminación en curso");
        alert.setContentText("¿Confirma que desea eliminar el item "+productTextField.getText()+" - "+brandTextField.getText()+" - "+descriptionTextField.getText()+"?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK) {
            try {
                if(productDAO.delete(productIdSpinner.getValue())){
                    alert2.setTitle("Eliminación");
                    alert2.setHeaderText("Eliminación exitosa.");
                    alert2.setContentText(null);
                    alert2.showAndWait();
                    setUpdatedValueFactoryIdSpinner();
                    defaultOverview();
                }else {
                    alert2.setAlertType(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setHeaderText("Actualización no realizada.");
                    alert2.showAndWait();
                }
            }catch (Exception ex){
               ex.printStackTrace();
            }
        }
    }

    public void newProductButtonOnAction(ActionEvent event) throws SQLException, FileNotFoundException {
        defaultOverview();
    }

    public void lockStatusComponents(boolean state){
        addGroupImageView.setDisable(state);
        addGroupImageView.setVisible(!state);
        productRadioButton.setDisable(state);
        serviceRadioButton.setDisable(state);
        productTextField.setDisable(state);
        brandTextField.setDisable(state);
        descriptionTextField.setDisable(state);
        groupComboBox.setDisable(state);
        groupComboBox.setEditable(false);
        priceTextField.setDisable(state);
    }

    public void visibleStatusComponents(boolean state){
        editHBox.visibleProperty().setValue(state);
    }

    public void buildImageProduct(InputStream inputStream) throws IOException {
        OutputStream outputStream = new FileOutputStream("image.jpg");

        byte[] contents = new byte[1024];
        int size = 0;
        while ((size = inputStream.read(contents)) != -1) {
            outputStream.write(contents, 0, size);
        }

//        image = new Image("file:image.jpg", productImageView.getFitWidth(), productImageView.getFitHeight(), true, true);
        imageFile = new File("image.jpg");
        image = new Image(imageFile.toURI().toString());
        productImageView.setImage(image);
        productImageView.setPreserveRatio(true);
        productDTO.setImage(new FileInputStream(imageFile));
    }

    public void clearProductRegistration(){
        productRadioButton.setSelected(true);
        productTextField.clear();
        brandTextField.clear();
        descriptionTextField.clear();
        groupComboBox.setValue(groupComboBox.getItems().get(0));
        priceTextField.clear();
    }

    public boolean validateFields() {
        int errorCounter = 0;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setTitle("Datos inválidos");
        if (productRadioButton.isSelected()==false&&serviceRadioButton.isSelected()==false){
            alert.setHeaderText("Identificación no realizada");
            alert.setContentText("Indique si el registro corresponde a un producto o servicio seleccionando la casilla correspondiente.");
            alert.showAndWait();
            errorCounter++;
        }
        if (productImageView.getImage()==null){
            alert.setHeaderText("Error de imagen");
            alert.setContentText("No se ha seleccionado ninguna imagen válida para el registro.");
            alert.showAndWait();
            errorCounter++;
        }
       if (productTextField.getText().isEmpty()) {
           productErrorLabel.setText("Ingrese el nombre del producto o servicio.");
           errorCounter++;
       }else{
            productErrorLabel.setText("");
       }

       if (brandTextField.getText().isEmpty()) {
           if (productRadioButton.isSelected()) {
               brandErrorLabel.setText("Ingrese la marca del producto.");
               errorCounter++;
           }
       }else{
            brandErrorLabel.setText("");
       }

       if (descriptionTextField.getText().isEmpty()) {
            descriptionErrorLabel.setText("Ingrese una descripción para el producto o servicio.");
            errorCounter++;
       } else{
           descriptionErrorLabel.setText("");
       }

       if (groupComboBox.getValue().toString().isEmpty()){
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
      }catch (Exception e){
           priceErrorLabel.setText("Ingrese un formato de número válido.");
           priceTextField.clear();
           errorCounter++;
      }

      if (errorCounter>0){
          return false;
      }else {
          return true;
      }
    }

    public void searchProduct() throws SQLException, IOException {

        if (productIdSpinner.getValue()!=null) {
            try {
                if (productDAO.read(productIdSpinner.getValue())!= null){
                    productDTO = productDAO.read(productIdSpinner.getValue());
                    productTextField.setText(productDTO.getName());
                    brandTextField.setText(productDTO.getBrand());
                    descriptionTextField.setText(productDTO.getDescription());
                    priceTextField.setText(String.valueOf(productDTO.getPrice()));
                    if(groupDAO.read(productDTO.getGroupId()) != null) {
                        groupDTO = groupDAO.read(productDTO.getGroupId());
                        groupComboBox.setValue(groupDTO.getGroupName());
                    }
                    buildImageProduct(productDTO.getImage());
                    lockStatusComponents(true);
                    visibleStatusComponents(true);
                }else{
                    System.out.println("producto no encontrado, estableciendo valores por default");
                    defaultOverview();
                }
            } catch (SQLException | NullPointerException ex) {
                System.out.println("error en catch");
                ex.printStackTrace();
            }
        }
    }

    public void defaultOverview() throws SQLException, FileNotFoundException {
        defaultValueIdSpinner();
        defaultValueGroupCombobox();
        defaultImageProduct();
        clearProductRegistration();
        lockStatusComponents(false);
        visibleStatusComponents(false);
    }

    public void defaultSearchView() throws SQLException {
        lockStatusComponents(true);
        visibleStatusComponents(true);
    }

    public void defaultImageProduct() throws FileNotFoundException {
        File addImageFile = new File("src/img/addImage-x512.png");
        Image addImage = new Image(addImageFile.toURI().toString());
        productImageView.setImage(addImage);
        productDTO.setImage(new FileInputStream(addImageFile));
    }

    public void defaultValueIdSpinner() {
        idProductsList = products.getUpdateIdProductList();
        newId = (int) idProductsList.get(idProductsList.size()-1);
        productIdSpinner.getValueFactory().setValue(newId);
    }

    public void defaultValueGroupCombobox(){
        groupComboBox.setValue(groupComboBox.getItems().get(0));
    }

    public void setUpdatedListCombobox(){
        groupComboBox.setItems(group.getUpdateNameGroupList());
    }

    public void setUpdatedValueFactoryIdSpinner(){
        idProductsList = products.getUpdateIdProductList();
        //llenando Spinner
        spinnerValueFactory = null;
        spinnerValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(idProductsList);
        spinnerValueFactory.setWrapAround(true);
        productIdSpinner.setValueFactory(spinnerValueFactory);
        spinnerValueFactory.valueProperty().addListener(((observableValue, integer, t1) -> {
            try {
                searchProduct();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        }));
        productIdSpinner.getStyleClass().add(productIdSpinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
    }

    @FXML
    public void min(MouseEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void close(MouseEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public class SpinnerListener implements ChangeListener<Integer>{
        @Override
        public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
                try{
                    searchProduct();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
        }
    }

}