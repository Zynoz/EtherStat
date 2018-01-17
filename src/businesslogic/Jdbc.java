package businesslogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class Jdbc {

    private Connection connection = null;

    private final String localIP = "192.168.0.102";
    private final String globalIP = "zynoz.ddns.net";
    private final String localUsername = "etherstat";
    private final String localPW = "Ma1997xi!";
    private final String piIP = "ourpi.ddns.net";
    private final String piUsername = "ether";
    private final String piPW = "etherstatpw";

    public void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void establishConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + localIP + "/etherstat" + "?user=" + localUsername + "&password=" + localPW);
            //connection = DriverManager.getConnection("jdbc:mysql://" + piIP + "/etherstat" + "?user=" + piUsername + "&password=" + piPW);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public void insert(Worker worker) {
        if (worker != null) {
            String sql = "INSERT INTO etherstat(name, avg, current, valid, stale, timest) VALUES(?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, worker.getWorker());
                preparedStatement.setDouble(2, worker.getAverageHashrate().doubleValue());
                preparedStatement.setDouble(3, worker.getCurrentHashrate().doubleValue());
                preparedStatement.setInt(4, worker.getValidShares());
                preparedStatement.setInt(5, worker.getStaleShares());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<Worker> getDbEntries() {
        ObservableList<Worker> workers = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM etherstat");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Worker worker = new Worker(resultSet.getInt("id"), resultSet.getString("name"), null, null, null, resultSet.getBigDecimal("current"), resultSet.getInt("valid"), 0, resultSet.getInt("stale"), resultSet.getBigDecimal("avg"), resultSet.getTimestamp("timest"));
                workers.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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