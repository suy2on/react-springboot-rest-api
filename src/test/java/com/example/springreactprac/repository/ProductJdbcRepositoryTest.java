package com.example.springreactprac.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

import com.example.springreactprac.model.Category;
import com.example.springreactprac.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup(){
        MysqldConfig config = aMysqldConfig(Version.v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository repository;

    // 모든 method마다 새로 생기지 않게
    private static Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);


    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다")
    void insertTest(){
        repository.insert(newProduct);
        List<Product> products = repository.findAll();
        assertThat(products.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName(){
        Optional<Product> product = repository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 id로 조회할 수 있다.")
    void testFindById(){
        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("상품을 Category으로 조회할 수 있다.")
    void testFindByCategory(){
        List<Product> product = repository.findByCategory(newProduct.getCategory());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("상품을 수정할 수 있다")
    void testUpdate(){
        newProduct.setProductName("updated-product");
        repository.update(newProduct);

        Optional<Product> updateProduct = repository.findById(newProduct.getProductId());
        assertThat(updateProduct.isEmpty(), is(false));
        assertThat(updateProduct.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    @DisplayName("상품을 전체삭제할 수 있다.")
    void testDeleteALl(){
        repository.deleteAll();
        List<Product> products = repository.findAll();
        assertThat(products.isEmpty(), is(true));
    }



}