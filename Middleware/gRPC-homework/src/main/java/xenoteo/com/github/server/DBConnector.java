package xenoteo.com.github.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnector {
    private Connection connection;

    public DBConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect();
        } catch(ClassNotFoundException e) {
            System.err.println("Unable to connect to database");
        }
    }

    public void connect(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:mo.db");
        } catch (SQLException throwables) {
            System.err.println("Unable to connect to database");
        }
    }

    public void setClientWaiting(int clientId) {
        try {
            String sql = "update clients set waiting = 1 where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clientId);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.err.println("Unable to connect to database");
        }
    }

    public void updateClientLastResponse(int clientId, String response) {
        try {
            String sql = "update clients set waiting = 0, response = ?, collected = 0 where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, response);
            preparedStatement.setInt(2, clientId);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.err.println("Unable to connect to database");
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                System.err.println("Unable to disconnect from database");
            }
        }
    }
}
