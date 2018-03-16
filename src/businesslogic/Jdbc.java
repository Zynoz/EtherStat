package businesslogic;

import exceptions.MySQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class Jdbc {

    private Connection connection = null;
    private String ip = "84.114.18.45:3307";
    private String username = "Ether";
    private String password = "S@tis1c";

    public void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void establishConnection() throws MySQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/ether" + "?user=" + username + "&password=" + password + "&useSSL=false");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println("could not establish connection");
            throw new MySQLException("Could not establish connection to database");
        }
    }

    public ObservableList<Worker> getDbEntries() throws MySQLException {
        ObservableList<Worker> workers = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM ether");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Worker worker = new Worker(resultSet.getInt("id"), resultSet.getString("name"), null, resultSet.getBigDecimal("current"), resultSet.getInt("valid"), 0, resultSet.getInt("stale"), resultSet.getBigDecimal("avg"), resultSet.getTimestamp("timest"));
                workers.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return workers;
    }
}