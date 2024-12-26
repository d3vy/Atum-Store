package com.clothing.customer.repositories;


import com.clothing.customer.models.FavoriteProduct;
import reactor.core.publisher.Mono;

public interface FavoriteProductRepository {

    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    Mono<Void> deleteByProductId(Integer productId);

    Mono<FavoriteProduct> findByProductId(Integer productId);
}
