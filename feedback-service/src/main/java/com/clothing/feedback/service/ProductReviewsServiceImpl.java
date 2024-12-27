package com.clothing.feedback.service;

import com.clothing.feedback.models.ProductReview;
import com.clothing.feedback.repositories.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductReviewsServiceImpl implements ProductReviewsService {

    private final ProductReviewRepository productReviewRepository;

    @Override
    public Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review) {
        return this.productReviewRepository.save(
                new ProductReview(UUID.randomUUID(), productId, rating, review)
        );
    }

    @Override
    public Flux<ProductReview> findProductReviewsBytProductId(Integer productId) {
        return productReviewRepository.findAllByProductId(productId);
    }
}
