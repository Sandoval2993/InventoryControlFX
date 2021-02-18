package dao;

import connection.DatabaseConnection;
import dto.UserDTO;
import interfaces.IDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IDAO<UserDTO> {
    private static final String SQL_CREATE ="INSERT INTO USERS (first_name, last_name, user_name, password, state, image) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_READ = "SELECT * FROM USERS WHERE user_id = ?";
    private static final String SQL_READALL = "SELECT * FROM USERS";
    private static final String SQL_UPDATE = "UPDATE USERS SET first_name=?, last_name=?, user_name=?,password=?,state=?,image=? WHERE user_id=?";
    private static final String SQL_DELETE = "DELETE FROM USERS WHERE user_id=?";
    private DatabaseConnection cnn = DatabaseConnection.getInstance();

    @Override
    public boolean create(UserDTO p) throws SQLException {
        try{
            PreparedStatement ps;
            ps = cnn.getCnn().prepareStatement(SQL_CREATE);
            ps.setString(1, p.getFirstName());
            ps.setString(2,p.getLastName());
            ps.setString(3,p.getUserName());
            ps.setString(4,p.getPassword());
            ps.setString(5,p.getState());
            ps.setBytes(6, p.getImageBytes());
            System.out.println(p);
            if (ps.executeUpdate()>0){
                System.out.println("Usuario creado");
                return true;
            }
        }catch (SQLException e){
            e.getMessage();
        } finally {
            cnn.closeConnection();
        }
        return false;
    }

    @Override
    public UserDTO read(Object key) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        UserDTO userDTO = null;
        try{
            ps = cnn.getCnn().prepareStatement(SQL_READ);
            ps.setString(1,key.toString());
            rs = ps.executeQuery();
            while (rs.next()){
                userDTO = new UserDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBytes(7));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            cnn.closeConnection();
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> readAll() throws SQLException {
        PreparedStatement ps;
        ArrayList<UserDTO> users = new ArrayList<>();
        ResultSet rs;

        try {
            ps = cnn.getCnn().prepareStatement(SQL_READALL);
            rs = ps.executeQuery();
            while (rs.next()){
                users.add(new UserDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBytes(7)));
            }
        }catch (SQLException ex){
            ex.getMessage();
        }finally {
            cnn.closeConnection();
        }
        return users;
    }

    @Override
    public boolean update(UserDTO p) throws SQLException {
        try{
            PreparedStatement ps;
            ps = cnn.getCnn().prepareStatement(SQL_UPDATE);
            ps.setString(1, p.getUserName());
            ps.setString(2,p.getLastName());
            ps.setString(3,p.getUserName());
            ps.setString(4,p.getPassword());
            ps.setString(5,p.getState());
            ps.setBytes(6, p.getImageBytes());
            ps.setInt(7,p.getUserId());

            if (ps.executeUpdate()>0){
                System.out.println("Usuario actualizado");
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cnn.closeConnection();
        }
        return false;
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
