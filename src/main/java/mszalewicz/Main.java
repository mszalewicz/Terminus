package mszalewicz;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class);
    private static final double appVersion = 0.53; // TODO: add mechanism to set application version during publishing

    static void main() {
        logger.info("Starting application...");
        logger.info("Initializing database...");

        boolean dbInitialized = Database.init("db.sqlite", appVersion);

        if (!dbInitialized) {
            logger.info("Shutting down application - cannot create initialize.");
            System.exit(1);
        }
    }
}