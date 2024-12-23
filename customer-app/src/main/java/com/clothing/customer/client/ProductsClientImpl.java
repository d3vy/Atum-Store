package com.clothing.customer.client;

import com.clothing.customer.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ProductsClientImpl implements ProductsClient {

    private final WebClient webClient;

    @Override
    public Flux<Product> findAllProducts(String filter) {
        return this.webClient
                .get()
                .uri("/atum-api/products?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
