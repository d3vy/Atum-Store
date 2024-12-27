package com.clothing.feedback.service;


import com.clothing.feedback.models.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<FavoriteProduct> addProductToFavorites(Integer productId);

    Mono<Void> removeProductFromFavorites(Integer productId);

    Mono<FavoriteProduct> findFavoriteProductByProductId(Integer productId);

    Flux<FavoriteProduct> findAllFavoriteProducts();
}
