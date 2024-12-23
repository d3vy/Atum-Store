package com.clothing.customer.client;

import com.clothing.customer.models.Product;
import reactor.core.publisher.Flux;

public interface ProductsClient {

    Flux<Product> findAllProducts(String filter);
}
