package services;

import java.sql.*;

public class DatabaseConnector {
    protected String url = "jdbc:oracle:thin:@localhost:1521:xe";
    protected String user ="SYSTEM";
    protected  String password ="123456";
    Connection connection;
    public void setConnection(){
        try{
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public ResultSet resultSet(String query){
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }catch (SQLException e){
            System.out.println( e.getMessage());
            return null;
        }
        return resultSet;
    }
}
