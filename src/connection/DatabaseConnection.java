package connection;

import java.sql.*;

public class DatabaseConnection {
    public static DatabaseConnection instance;
    private Connection cnn;
    private final String DB = "InventoryControl";
    private final String USER = "sa";
    private final String PASS = "12110193";
    private final String URL = "jdbc:sqlserver://localhost:1433;database=" + DB + ";integratedSecurity=false;user=" + USER + ";password=" + PASS;

    private DatabaseConnection() {
        try {
            cnn = DriverManager.getConnection(URL);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Error de conexión");
        }
    }

    public synchronized static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else {
        }
        return instance;
    }

    public Connection getCnn(){
        return cnn;
    }
    public void closeConnection() throws SQLException {
//        if (cnn!=null){
//            cnn.close();
            this.instance = null;
//            System.out.println("Disconnected");
//        }
    }

//    public void findConnection() throws SQLException{
//        String verifyLogin = "SELECT count(1) FROM user_account WHERE username='admin' AND password='admin'";
//        try{
//            Statement statement = instance.getCnn().createStatement();
//            ResultSet queryResult = statement.executeQuery(verifyLogin);
//
//            while (queryResult.next()){
//                if (queryResult.getInt(1)==1){
//                    System.out.println("Correcto");
//                }else {
//                    System.out.println("Incorrecto");
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            e.getCause();
//        }
//    }
    }




