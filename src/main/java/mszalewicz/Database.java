package mszalewicz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.UUID;

public class Database {
    public static Connection connection;
    private static double appVersion;

    private Database(){};

    public static synchronized boolean init(String db_pathname, double version) {
        String url = "jdbc:sqlite:" + db_pathname;
        boolean initialized = true;
        boolean initializationError = false;

        try {
            connection = DriverManager.getConnection(url);
            appVersion = version;
        } catch (SQLException e) {
            Main.logger.error("Failed to connect to database" + db_pathname, e);
            return initializationError;
        }

        try {
            var scheme = Files.readString(Path.of("schema.sql"));
            CreateTables(scheme);
            GetAppId();
        } catch (IOException e){
            Main.logger.error("Error reading schema.sql: ", e);
            return initializationError;
        } catch (RuntimeException e) {
            Main.logger.error("Error creating tables: ", e);
            return initializationError;
        }

        return initialized;
    }

    public static void CreateTables(String schema) {
        try {
            connection.createStatement().executeUpdate(schema);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table(s) from scheme.", e);
        }
    }

    public static void SetAppInternalId(String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO settings(id, version) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setDouble(2, appVersion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query for setting app id.", e);
        }
    }

    public static String GetAppId() {
        try {
            String appId;

            ResultSet result = connection.createStatement().executeQuery("SELECT id FROM settings;");

            boolean hasRows = result.next();

            if  (hasRows) {
                appId = result.getString("id");
            } else {
                UUID uuid = UUID.randomUUID();
                appId = uuid.toString();
                SetAppInternalId(appId);
            }

            return appId;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query for app id.", e);
        }
    }
}