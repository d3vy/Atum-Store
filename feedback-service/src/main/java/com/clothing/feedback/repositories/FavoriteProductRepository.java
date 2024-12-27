package com.clothing.feedback.repositories;


import com.clothing.feedback.models.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductRepository {

    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    Mono<Void> deleteByProductId(Integer productId);

    Mono<FavoriteProduct> findByProductId(Integer productId);

    Flux<FavoriteProduct> findAll();
}
