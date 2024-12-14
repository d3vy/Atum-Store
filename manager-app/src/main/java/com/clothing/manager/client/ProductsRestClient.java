package com.clothing.manager.client;

import com.clothing.manager.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRestClient {
    List<Product> findAllProducts();

    Product createProduct(String title, String description);

    Optional<Product> findProduct(Integer productId);

    void updateProduct(Integer productId, String title, String description);

    void deleteProduct(Integer productId);
}
