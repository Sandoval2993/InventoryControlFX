package model;

import dao.GroupDAO;
import dao.ProductDAO;
import dto.ProductDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Products {
    private ProductDAO productDAO;
    private int lastId;

    public Products(){
    }

    public ObservableList getUpdateIdProductList(){
        ObservableList<Integer> updateIdProductList = FXCollections.observableArrayList();
        try {
            productDAO = new ProductDAO();
            ArrayList<ProductDTO> listProducts = (ArrayList<ProductDTO>) productDAO.readAll();
            //            idProductList.addListener(new ListChangeListener<Integer>() {
            //                @Override
            //                public void onChanged(Change<? extends Integer> change) {
            //                    System.out.println("Ocurrió un cambio");
            //                }
            //            });
            Iterator iteratorProducts = listProducts.iterator();
            while (iteratorProducts.hasNext()) {
                ProductDTO productDTO = (ProductDTO) iteratorProducts.next();
                updateIdProductList.add(productDTO.getProductId());
                lastId = productDTO.getProductId();
            }
            updateIdProductList.add(lastId + 1);
            return updateIdProductList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updateIdProductList;
    }

    public ArrayList<ProductDTO> getProductList() throws SQLException {
        productDAO = new ProductDAO();
        ArrayList<ProductDTO> listProducts = productDAO.readAll();
        return listProducts;
    }
}
