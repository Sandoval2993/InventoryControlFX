package model;

import dao.ProductDAO;
import dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class Product {
    private ProductDAO productDAO;
    private int lastId;

    public Product(){
    }

    public ArrayList<ProductDTO> getProductList() throws SQLException {
        productDAO = new ProductDAO();
        ArrayList<ProductDTO> listProducts = productDAO.readAll();
        return listProducts;
    }
}
