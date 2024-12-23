package com.clothing.customer.config;

import com.clothing.customer.client.ProductsClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsClientImpl productsClientImpl(
            @Value("${atum.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl
    ) {
        return new ProductsClientImpl(WebClient.builder()
                .baseUrl(catalogueBaseUrl)
                .build());
    }
}
