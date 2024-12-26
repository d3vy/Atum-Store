package com.clothing.customer.service;


import com.clothing.customer.models.FavoriteProduct;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<FavoriteProduct> addProductToFavorites(Integer productId);

    Mono<Void> removeProductFromFavorites(Integer productId);

    Mono<FavoriteProduct> findFavoriteProductByProductId(Integer productId);
}
