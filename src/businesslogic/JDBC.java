package businesslogic;

import java.sql.*;

public class JDBC {

    private Connection connection = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void establishConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.0.102/etherstat?user=etherstat&password=Ma1997xi!");
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT workerName FROM etherstat");

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignored) {
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ignored) {
                }
                stmt = null;
            }
        }
    }

    public void insert(Worker worker) {
        if (worker != null) {
            String sql = "INSERT INTO etherstat(name, avg, current, valid, stale) VALUES(?, ?, ?, ?, ?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, worker.getWorker());
                preparedStatement.setDouble(2, worker.getAverageHashrate().doubleValue());
                preparedStatement.setDouble(3, worker.getCurrentHashrate().doubleValue());
                preparedStatement.setInt(4, worker.getValidShares());
                preparedStatement.setInt(5, worker.getStaleShares());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}