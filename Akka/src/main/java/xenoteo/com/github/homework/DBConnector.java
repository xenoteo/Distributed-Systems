package xenoteo.com.github.homework;

import java.sql.*;

/**
 * The class responsible for maintaining the database connection.
 */
public class DBConnector {

    /**
     * THe connection with database.
     */
    private static Connection connection;

    /**
     * Creates the connection to the database.
     */
    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:satellites.sqlite");
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects from the database.
     */
    public static void disconnect(){
        try {
            if (connection != null)
                connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes all satellites error values to 0.
     */
    public static void reinitializeDB(){
        try{
            Statement statement = connection.createStatement();

            statement.executeUpdate("drop table if exists satellite");
            statement.executeUpdate("create table satellite (id integer, errors integer)");

            String sql = "insert into satellite values(?, 0)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int id = 100; id < 200; id++) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Increments the number of errors of the satellite of the provided ID.
     *
     * @param satelliteId  the satellite ID
     */
    public static void incrementErrors(int satelliteId){
        try{
            int newErrors = getErrors(satelliteId) + 1;
            String sql = "update satellite set errors = ? where id = ?";
            PreparedStatement writePreparedStatement = connection.prepareStatement(sql);
            writePreparedStatement.setInt(1, newErrors);
            writePreparedStatement.setInt(2, satelliteId);
            writePreparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Reads the number of errors of the satellite of the provided ID from the database.
     *
     * @param satelliteId  the satellite ID
     * @return the number of errors of the required satellite
     */
    public static int getErrors(int satelliteId){
        try{
            String sql = "select errors from satellite where id = ?";
            PreparedStatement readPreparedStatement = connection.prepareStatement(sql);
            readPreparedStatement.setInt(1, satelliteId);
            return readPreparedStatement.executeQuery().getInt("errors");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }
}
