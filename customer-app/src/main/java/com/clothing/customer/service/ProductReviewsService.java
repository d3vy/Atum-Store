package com.clothing.customer.service;

import com.clothing.customer.models.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsService {

    Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review);
    Flux<ProductReview> findProductReviewsBytProductId(Integer productId);
}
