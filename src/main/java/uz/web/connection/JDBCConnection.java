package uz.web.connection;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
    private static Connection connection;

    static {
        try {
            Class.forName("org.h2.Driver");
            String h2Url = "jdbc:h2:./database_product/product";
            String username = "sa";
            String password = "";
            connection = DriverManager.getConnection(h2Url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }


}
