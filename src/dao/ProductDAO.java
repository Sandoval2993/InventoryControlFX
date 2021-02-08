package dao;

import connection.DatabaseConnection;
import dto.ProductDTO;
import interfaces.IDAO;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IDAO<ProductDTO> {
private static final String SQL_CREATE ="INSERT INTO Products (Product, Brand, Description,Group_id,Inventoryable,Price,Image) VALUES (?,?,?,?,?,?,?)";
private static final String SQL_READ = "SELECT * FROM Products WHERE Product_id = ?";
private static final String SQL_READALL = "SELECT * FROM Products";
private static final String SQL_UPDATE = "UPDATE Products SET Product=?, Brand=?, Description=?,Group_id=?,Inventoryable=?,Price=?,Image=? WHERE Product_id=?";
private static final String SQL_DELETE = "DELETE FROM Products WHERE Product_id=?";
private DatabaseConnection cnn = DatabaseConnection.getInstance();
private FileInputStream fileInputStream;
private File imageFile;
private Image image;
    @Override
    public boolean create(ProductDTO p) throws SQLException {
       try{
           PreparedStatement ps;
           ps = cnn.getCnn().prepareStatement(SQL_CREATE);
           ps.setString(1, p.getName());
           ps.setString(2,p.getBrand());
           ps.setString(3,p.getDescription());
           ps.setInt(4,p.getGroupId());
           ps.setString(5,p.getInventoryable());
           ps.setDouble(6,p.getPrice());
           ps.setBinaryStream(7, p.getImage());
           System.out.println(p);
           if (ps.executeUpdate()>0){
               return true;
           }
       }catch (SQLException e){
           e.getMessage();
       } finally {
           System.out.println("Producto creado");
           cnn.closeConnection();
       }
       return false;
    }

    @Override
    public boolean update(ProductDTO p) throws SQLException {
        try{
            PreparedStatement ps;
            ps = cnn.getCnn().prepareStatement(SQL_UPDATE);
            ps.setString(1, p.getName());
            ps.setString(2,p.getBrand());
            ps.setString(3,p.getDescription());
            ps.setInt(4,p.getGroupId());
            ps.setString(5,p.getInventoryable());
            ps.setDouble(6,p.getPrice());
            ps.setBinaryStream(7, p.getImage());
            ps.setInt(8,p.getProductId());

            if (ps.executeUpdate()>0){
                return true;
            }
        }catch (SQLException e){
            e.getMessage();
        }finally {
            System.out.println("Producto actualizado");
            cnn.closeConnection();
        }
        return false;
    }

    @Override
    public ProductDTO read(Object key) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        ProductDTO productDTO = null;
        try{
            ps = cnn.getCnn().prepareStatement(SQL_READ);
            ps.setString(1,key.toString());

            rs = ps.executeQuery();

            while (rs.next()){
//                productDTO = new ProductDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getBinaryStream(8));
                Blob blob = rs.getBlob(8);
                productDTO = new ProductDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7), blob.getBinaryStream());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            cnn.closeConnection();
        }
        return productDTO;
    }

    @Override
    public List<ProductDTO> readAll() throws SQLException {
        PreparedStatement ps;
        ArrayList<ProductDTO> products = new ArrayList<>();
        ResultSet rs;

        try {
            ps = cnn.getCnn().prepareStatement(SQL_READALL);

            rs = ps.executeQuery();

            while (rs.next()){
                Blob blob = rs.getBlob(8);
                products.add(new ProductDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7), blob.getBinaryStream()));
            }
        }catch (SQLException ex){
            ex.getMessage();
        }finally {
            cnn.closeConnection();
        }
        return products;
    }

    @Override
    public boolean delete(Object key) throws SQLException {
        try {
            PreparedStatement ps;

            ps = cnn.getCnn().prepareStatement(SQL_DELETE);
            ps.setString(1,key.toString());
            if (ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            cnn.closeConnection();
        }
        return false;
    }
}
