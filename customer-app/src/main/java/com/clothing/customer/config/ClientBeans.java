package com.clothing.customer.config;

import com.clothing.customer.client.FavoriteProductsClientImpl;
import com.clothing.customer.client.ProductReviewsClientImpl;
import com.clothing.customer.client.ProductsClientImpl;
import de.codecentric.boot.admin.client.config.ClientProperties;
import de.codecentric.boot.admin.client.registration.ReactiveRegistrationClient;
import de.codecentric.boot.admin.client.registration.RegistrationClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientBeans {

    // Prototype - делает так, чтобы экземпляр этого бина при каждой вызове создавался снова.
    // Prototype не внедряется в контекст приложения, поэтому при нужде это нужно делать самостоятельно.
    // (По умолчанию Singleton)
    // Здесь можно прописывать общие характеристики для каждого из последующих методов.
    @Bean
    @Scope("prototype")
    public WebClient.Builder atumServicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository, authorizedClientRepository);
        filter.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                .filter(filter);
    }

    @Bean
    public ProductsClientImpl productsClientImpl(
            @Value("${atum.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl,
            WebClient.Builder atumServicesWebClientBuilder
    ) {
        return new ProductsClientImpl(atumServicesWebClientBuilder
                .baseUrl(catalogueBaseUrl)
                .build());
    }

    @Bean
    public FavoriteProductsClientImpl favoriteProductsClientImpl(
            @Value("${atum.services.feedback.uri:http://localhost:8084}") String feedbackBaseUrl,
            WebClient.Builder atumServicesWebClientBuilder
    ) {
        return new FavoriteProductsClientImpl(atumServicesWebClientBuilder
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    public ProductReviewsClientImpl productReviewsClientImpl(
            @Value("${atum.services.reviews.uri:http://localhost:8084}") String reviewsBaseUrl,
            WebClient.Builder atumServicesWebClientBuilder
    ) {
        return new ProductReviewsClientImpl(atumServicesWebClientBuilder
                .baseUrl(reviewsBaseUrl)
                .build());
    }

    @Bean
    @ConditionalOnProperty(name = "spring.boot.admin.client.enabled", havingValue = "true")
    public RegistrationClient registrationClient(
            ClientProperties clientProperties,
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ReactiveOAuth2AuthorizedClientService authorizedClientService
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                authorizedClientService));
        filter.setDefaultClientRegistrationId("metrics");

        return new ReactiveRegistrationClient(WebClient.builder()
                .filter(filter)
                .build(), clientProperties.getReadTimeout());
    }

}
