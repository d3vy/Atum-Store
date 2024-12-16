package com.clothing.atum.services;


import com.clothing.atum.models.Product;

import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAllProducts(String filter);

    Product createProduct(String title, String description);

    Optional<Product> findProduct(Integer productId);

    void updateProduct(Integer id, String title, String description);

    void deleteProduct(Integer id);
}
