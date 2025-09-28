package mszalewicz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Database database;
    private static final double appVersion = 0.5; // TODO: add mechanism to set application version during publishing

    static void main() {
        logger.info("Starting application...");

        database = InitializeDatabase();

        if(database == null) {
            logger.info("Shutting down application - cannot create database.");
            System.exit(1);
        }
    }

    static Database InitializeDatabase() {
        logger.info("Creating database if does not exist...");

        Database database = null;

        try {
            database = new Database("db.sqlite", appVersion);
        } catch (RuntimeException e) {
            logger.error("Error initializing database: ",  e);
            return null;
        }

        try {
            var scheme = Files.readString(Path.of("schema.sql"));
            database.CreateTables(scheme);
            database.GetAppName();
        } catch (IOException e){
            logger.error("Error reading schema.sql: ", e);
        } catch (RuntimeException e) {
            logger.error("Error creating tables: ", e);
        }

        return database;
    }
}


