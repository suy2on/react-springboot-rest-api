package com.example.springreactprac.repository;

import com.example.springreactprac.model.Category;
import com.example.springreactprac.model.Product;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.example.springreactprac.JdbcUtils.*;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        int update = jdbcTemplate.update(
            "INSERT INTO products(product_id, product_name ,category, price, description, created_at ,updated_at)"
                +
                "VALUES(UUID_TO_BIN(:productId), :productName, :category, :price, :description , :createdAt, :updatedAt)", toParamMap(product));
        if(update != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {

        int update = jdbcTemplate.update(
            "update products set product_name = :productName, category = :category, price = :price, description = :description, created_at = :createdAt, updated_at = :updatedAt "
                + "where product_id = UUID_TO_BIN(:productId)",
            toParamMap(product));

        if(update != 1){
            throw new RuntimeException("Noting was updated");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.of(jdbcTemplate
                .queryForObject("select * from products where product_id = UUID_TO_BIN(:productId)",
                    Collections.singletonMap("productId", productId.toString().getBytes()),
                    productRowMapper));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.of(jdbcTemplate
                .queryForObject("select * from products where product_name = :productName",
                    Collections.singletonMap("productName", productName),
                    productRowMapper));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate
            .query("select * from products where category = :category",
                Collections.singletonMap("category", category.toString()),
                productRowMapper); // collections는 optional 쓸필요 없음

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from products", Collections.emptyMap());
    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        String productName = resultSet.getString("product_name");
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        LocalDateTime created_at = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Product(productId, productName, category, price, description, created_at, updatedAt);
    };

    private Map<String, Object> toParamMap(Product product){
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("productId", product.getProductId().toString().getBytes());
        objectHashMap.put("productName", product.getProductName());
        objectHashMap.put("price", product.getPrice());
        objectHashMap.put("description", product.getDescription());
        objectHashMap.put("category", product.getCategory().toString());
        objectHashMap.put("createdAt", product.getCreatedAt());
        objectHashMap.put("updatedAt", product.getUpdatedAt());

        return objectHashMap;

    }
}
