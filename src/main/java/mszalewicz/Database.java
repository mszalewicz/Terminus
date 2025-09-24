package mszalewicz;

import java.sql.*;

public class Database {

    public final Connection connection;

    Database(String pathname) {
        String url = "jdbc:sqlite:" + pathname;

        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to connect to database " + pathname, e);
        }
    }

    void CreateTables(String schema) {
        try {
            connection.createStatement().executeUpdate(schema);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table(s) from scheme.", e);
        }
    }
}
