package com.clothing.customer.repositories;

import com.clothing.customer.models.ProductReview;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProductReviewRepositoryImpl implements ProductReviewRepository {

    private final List<ProductReview> productReviewList = Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<ProductReview> save(ProductReview productReview) {
        this.productReviewList.add(productReview);
        return Mono.just(productReview);
    }

    @Override
    public Flux<ProductReview> findAllByProductId(Integer productId) {
        return Flux.fromIterable(this.productReviewList)
                .filter(productReview -> productReview.getProductId().equals(productId));
    }
}
