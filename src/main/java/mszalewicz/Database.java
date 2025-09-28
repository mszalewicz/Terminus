package mszalewicz;

import java.sql.*;
import java.util.UUID;

public class Database {

    public final Connection connection;
    private static double appVersion;


    Database(String pathname, double version) {
        String url = "jdbc:sqlite:" + pathname;

        try {
            connection = DriverManager.getConnection(url);
            appVersion = version;
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

    void SetName(String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO settings(name, version) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setDouble(2, appVersion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query for app name.", e);
        }
    }

    String GetAppName() {
        try {
            String name;

            ResultSet result = connection.createStatement().executeQuery("SELECT name FROM settings;");

            boolean hasRows = result.next();

            if  (hasRows) {
                name = result.getString("name");
            } else {
                UUID uuid = UUID.randomUUID();
                name = uuid.toString();
                SetName(name);
            }

            return name;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query for app name.", e);
        }
    }
}