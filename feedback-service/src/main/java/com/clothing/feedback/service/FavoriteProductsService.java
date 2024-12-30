package com.clothing.feedback.service;


import com.clothing.feedback.models.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<FavoriteProduct> addProductToFavorites(Integer productId, String userId);

    Mono<Void> removeProductFromFavorites(Integer productId, String userId);

    Mono<FavoriteProduct> findFavoriteProductByProductId(Integer productId, String userId);

    Flux<FavoriteProduct> findAllFavoriteProducts(String userId);
}
