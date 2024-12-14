package com.clothing.manager.config;

import com.clothing.manager.client.ProductsRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean // Лучше использовать именно реализацию, а не интерфейс
    public ProductsRestClientImpl productsRestClient(@Value("${atum.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new ProductsRestClientImpl(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .build());
    }
}
