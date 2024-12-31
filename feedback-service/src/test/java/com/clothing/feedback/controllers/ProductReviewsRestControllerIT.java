package com.clothing.feedback.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@Slf4j
@SpringBootTest
@AutoConfigureWebTestClient
class ProductReviewsRestControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;


    @Test
    void findProductReviewsByProductId_ReturnsReviews() {
        // given
        // when
        this.webTestClient.mutateWith(mockJwt())
                .mutate().filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                    log.info("========== REQUEST ==========");
                    log.info("{} {}", clientRequest.method(), clientRequest.url());
                    clientRequest.headers().forEach((header, value) -> log.info("{}: {}", header, value));
                    log.info("======== END REQUEST ========");
                    return Mono.just(clientRequest);
                }))
                .build()
                .get()
                .uri("/feedback-api/product-reviews/by-product-id/1")
                .exchange()
                // then
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .json("""
                        [
                            {
                                "id": "bd7779c2-cb05-11ee-b5f3-df46a1249898",
                                "productId": 1,
                                "rating": 1,
                                "review": "Отзыв №1",
                                "userId": "user-1"
                            },
                            {
//                            "id": "be424abc-cb05-11ee-ab16-2b747e61f570",
//                            "productId": 1,
//                            "rating": 3,
//                            "review": "Отзыв №2",
//                            "userId": "user-2"
                            },
                            {
//                            "id": "be77f95a-cb05-11ee-91a3-1bdc94fa9de4",
//                            "productId": 1,
//                            "rating": 5,
//                            "review": "Отзыв №3",
//                            "userId": "user-3"
                            }
                        ]""");
    }


}