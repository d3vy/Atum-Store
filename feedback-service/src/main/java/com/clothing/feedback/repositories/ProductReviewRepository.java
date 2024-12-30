package com.clothing.feedback.repositories;

import com.clothing.feedback.models.ProductReview;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;


public interface ProductReviewRepository extends ReactiveCrudRepository<ProductReview, UUID> {

    Flux<ProductReview> findAllByProductId(Integer productId);
}
