package uz.web.dao;

import lombok.NoArgsConstructor;
import uz.web.connection.JDBCConnection;
import uz.web.entity.ProductType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class BaseDao<T> {
    protected final String tableName;
    private Connection connection;

    public BaseDao(String tableName) {
        this.tableName = tableName;
        connection = JDBCConnection.getConnection();
    }


    protected abstract T save(T entity);

    protected abstract Optional<T> findById(long id);

    protected abstract List<T> findAll(int page, int size);

    public boolean delete(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            statement.setLong(1, id);
            int i = statement.executeUpdate();
            if (i > 0) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

