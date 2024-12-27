package com.clothing.feedback.repositories;

import com.clothing.feedback.models.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewRepository {
    Mono<ProductReview> save(ProductReview productReview);
    Flux<ProductReview> findAllByProductId(Integer productId);
}
