package com.clothing.feedback.repositories;


import com.clothing.feedback.models.FavoriteProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface FavoriteProductRepository extends ReactiveCrudRepository<FavoriteProduct, UUID> {

    Mono<Void> deleteByProductId(Integer productId);

    Mono<FavoriteProduct> findByProductId(Integer productId);
}
