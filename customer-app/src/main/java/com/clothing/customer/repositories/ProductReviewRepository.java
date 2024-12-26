package com.clothing.customer.repositories;

import com.clothing.customer.models.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewRepository {
    Mono<ProductReview> save(ProductReview productReview);
    Flux<ProductReview> findAllByProductId(Integer productId);
}
