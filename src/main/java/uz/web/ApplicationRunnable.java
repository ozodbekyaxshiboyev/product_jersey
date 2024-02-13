package uz.web;

import uz.web.connection.JDBCConnection;
import java.sql.*;

public class ApplicationRunnable {
    private static Connection connection = JDBCConnection.getConnection();

    public static void runSqlCommands(){
        String createQuery = """
                CREATE TABLE IF NOT EXISTS productstatus (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) UNIQUE NOT NULL,
                    description VARCHAR(255)
                );
                                
                CREATE TABLE IF NOT EXISTS producttype (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) UNIQUE NOT NULL,
                    description VARCHAR(255)
                );
                                
                CREATE TABLE IF NOT EXISTS product (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) UNIQUE NOT NULL,
                    producttype BIGINT REFERENCES producttype(id),
                    productstatus BIGINT REFERENCES productstatus(id),
                    price DOUBLE PRECISION,
                    description VARCHAR(255)
                );
                                
                """;
        try {
            Statement statement = connection.createStatement();
            boolean execute = statement.execute(createQuery);
            if (execute) System.out.println("Successfully initialized");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
