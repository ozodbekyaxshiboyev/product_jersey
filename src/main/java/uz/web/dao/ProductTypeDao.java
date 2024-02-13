package uz.web.dao;

import uz.web.connection.JDBCConnection;
import uz.web.entity.ProductType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductTypeDao extends BaseDao<ProductType> {
    private static Connection connection;

    public ProductTypeDao(String tableName) {
        super(tableName);
        connection = JDBCConnection.getConnection();
    }

    @Override
    public ProductType save(ProductType productType) {
        if (productType.getId() == null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + tableName + " (name, description) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, productType.getName());
                statement.setString(2, productType.getDescription());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            productType.setId(generatedId);
                            return productType;
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

                statement.setString(1, productType.getName());
                statement.setString(2, productType.getDescription());
                statement.setLong(3, productType.getId());
                int i = statement.executeUpdate();
                if (i > 0) return productType;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public Optional<ProductType> findById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ProductType productType = mapResultSetToProductType(resultSet);
                    return Optional.of(productType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<ProductType> findAll(int page, int size) {
        List<ProductType> productTypes = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName + " ORDER BY id LIMIT ? OFFSET ?");
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, (page - 1) * size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductType productType = mapResultSetToProductType(resultSet);
                productTypes.add(productType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productTypes;
    }


    private ProductType mapResultSetToProductType(ResultSet resultSet) throws SQLException {
        return ProductType.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();

    }
}
