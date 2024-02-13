package uz.web.dao;

import uz.web.bean.BeanFactory;
import uz.web.connection.JDBCConnection;
import uz.web.dto.ProductDto;
import uz.web.entity.Product;
import uz.web.entity.ProductStatus;
import uz.web.entity.ProductType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductDao extends BaseDao<Product> {
    private static Connection connection;
    private final ProductTypeDao productTypeDao = BeanFactory.productTypeDao;
    private final ProductStatusDao productStatusDao = BeanFactory.productStatusDao;

    public ProductDao(String tableName) {
        super(tableName);
        connection = JDBCConnection.getConnection();
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + tableName + " (name, productType, productStatus, price, description) " +
                                "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, product.getName());
                statement.setLong(2, product.getProductType().getId());
                statement.setLong(3, product.getProductStatus().getId());
                statement.setDouble(4, product.getPrice());
                statement.setString(5, product.getDescription());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            product.setId(generatedId);
                        }
                    }
                }
                return product;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + tableName + " SET name = ?, productType = ?, productStatus = ?, price = ?, description = ? " +
                            "WHERE id = ?");

            statement.setString(1, product.getName());
            statement.setLong(2, product.getProductType().getId());
            statement.setLong(3, product.getProductStatus().getId());
            statement.setDouble(4, product.getPrice());
            statement.setString(5, product.getDescription());
            statement.setLong(6, product.getId());
            int i = statement.executeUpdate();
            if (i > 0) return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll(int page, int size) {
        List<Product> products = new LinkedList<>();
        try {
            String query = """
                    SELECT
                        p.id AS product_id,
                        p.name AS product_name,
                        p.price AS product_price,
                        p.description AS product_description,
                        pt.id AS product_type_id,
                        pt.name AS product_type_name,
                        pt.description AS product_type_description,
                        ps.id AS product_status_id,
                        ps.name AS product_status_name,
                        ps.description AS product_status_description
                    FROM
                        product p
                    JOIN
                        producttype pt ON p.producttype = pt.id
                    JOIN
                        productstatus ps ON p.productstatus = ps.id
                        LIMIT ? OFFSET ?                
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, (page - 1) * size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Optional<Product> findById(long id) {
        try {
            String query = """
                    SELECT
                        p.id AS product_id,
                        p.name AS product_name,
                        p.price AS product_price,
                        p.description AS product_description,
                        pt.id AS product_type_id,
                        pt.name AS product_type_name,
                        pt.description AS product_type_description,
                        ps.id AS product_status_id,
                        ps.name AS product_status_name,
                        ps.description AS product_status_description
                    FROM
                        product p
                    JOIN
                        producttype pt ON p.producttype = pt.id
                    JOIN
                        productstatus ps ON p.productstatus = ps.id
                        WHERE
                            p.id = ?
                        LIMIT 1;
                                        
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = mapResultSetToProduct(resultSet);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        ProductType productType = ProductType.builder()
                .id(resultSet.getLong("product_type_id"))
                .name(resultSet.getString("product_type_name"))
                .description(resultSet.getString("product_type_description"))
                .build();
        ProductStatus productStatus = ProductStatus.builder()
                .id(resultSet.getLong("product_status_id"))
                .name(resultSet.getString("product_status_name"))
                .description(resultSet.getString("product_status_description"))
                .build();
        Product build = Product.builder()
                .id(resultSet.getLong("product_id"))
                .name(resultSet.getString("product_name"))
                .price(resultSet.getDouble("product_price"))
                .description(resultSet.getString("product_description"))
                .productType(productType)
                .productStatus(productStatus)
                .build();
        return build;
    }

}

