package uz.web.dao;

import uz.web.connection.JDBCConnection;
import uz.web.entity.ProductStatus;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductStatusDao extends BaseDao<ProductStatus> {
    private static Connection connection;

    public ProductStatusDao(String tableName) {
        super(tableName);
        connection = JDBCConnection.getConnection();
    }

    @Override
    public ProductStatus save(ProductStatus productStatus) {
        if (productStatus.getId() == null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + tableName + " (name, description) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, productStatus.getName());
                statement.setString(2, productStatus.getDescription());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            productStatus.setId(generatedId);
                            return productStatus;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE " + tableName + " SET name = ?, description = ? WHERE id = ?");
                statement.setString(1, productStatus.getName());
                statement.setString(2, productStatus.getDescription());
                statement.setLong(3, productStatus.getId());

                int i = statement.executeUpdate();
                if (i>0) return productStatus;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public Optional<ProductStatus> findById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ProductStatus productStatus = mapResultSetToProductStatus(resultSet);
                    return Optional.of(productStatus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<ProductStatus> findAll(int page, int size) {
        List<ProductStatus> productStatusList = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName + " ORDER BY id LIMIT ? OFFSET ?");
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, (page - 1) * size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductStatus productStatus = mapResultSetToProductStatus(resultSet);
                productStatusList.add(productStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productStatusList;
    }


    private ProductStatus mapResultSetToProductStatus(ResultSet resultSet) throws SQLException {
        return ProductStatus.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }
}
