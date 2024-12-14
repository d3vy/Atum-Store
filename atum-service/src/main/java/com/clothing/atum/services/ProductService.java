package com.clothing.atum.services;


import com.clothing.atum.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    Product createProduct(String title, String description);

    Optional<Product> findProduct(Integer productId);

    void updateProduct(Integer id, String title, String description);

    void deleteProduct(Integer id);
}
