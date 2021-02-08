package dao;

import connection.DatabaseConnection;
import dto.GroupDTO;
import interfaces.IDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO implements IDAO<GroupDTO> {
    private static final String SQL_CREATE = "INSERT INTO Groups (Group_Name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE Groups SET Group_Name=?";
    private static final String SQL_READ = "SELECT * FROM Groups WHERE Group_Id=?";
    private static final String SQL_READALL = "SELECT * FROM Groups";

    private DatabaseConnection cnn = DatabaseConnection.getInstance();
    @Override
    public boolean create(GroupDTO g) throws SQLException {
        try {
            PreparedStatement ps;
            ps = cnn.getCnn().prepareStatement(SQL_CREATE);
            ps.setString(1,g.getGroupName());

            if (ps.executeUpdate()>0){
                System.out.println("Grupo creado");
                return true;
            }
        }catch (SQLException e){
            e.getMessage();
        }finally {
            cnn.closeConnection();
        }
        return false;
    }

    @Override
    public boolean delete(Object key) {
        return false;
    }

    @Override
    public boolean update(GroupDTO g) throws SQLException {
        return false;
    }

    @Override
    public GroupDTO read(Object key) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        GroupDTO groupDTO = null;
        try{
            ps = cnn.getCnn().prepareStatement(SQL_READ);
            ps.setString(1,key.toString());

            rs = ps.executeQuery();

            while (rs.next()){
                groupDTO = new GroupDTO(rs.getInt(1),rs.getString(2));
            }
        } catch (SQLException throwables) {
            System.out.println("error en read groupDAO");
            throwables.printStackTrace();
        }finally {
            cnn.closeConnection();
        }
        return groupDTO;
    }

    @Override
    public List<GroupDTO> readAll() throws SQLException {
        PreparedStatement ps;
        ArrayList<GroupDTO> groups = new ArrayList<>();
        ResultSet rs;

        try {
            ps = cnn.getCnn().prepareStatement(SQL_READALL);

            rs = ps.executeQuery();

            while (rs.next()){
                groups.add(new GroupDTO(rs.getInt(1),rs.getString(2)));
            }
        }catch (SQLException ex){
            ex.getMessage();
        }finally {
            cnn.closeConnection();
        }
        return groups;
    }
}
