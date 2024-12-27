package com.clothing.feedback.repositories;

import com.clothing.feedback.models.FavoriteProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class FavoriteProductRepositoryImpl implements FavoriteProductRepository {

    private final List<FavoriteProduct> favoriteProducts = Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct) {
        this.favoriteProducts.add(favoriteProduct);
        return Mono.just(favoriteProduct);
    }

    @Override
    public Mono<Void> deleteByProductId(Integer productId) {
        this.favoriteProducts.removeIf(favoriteProduct -> favoriteProduct.getProductId().equals(productId));
        return Mono.empty();
    }

    @Override
    public Mono<FavoriteProduct> findByProductId(Integer productId) {
        return Flux.fromIterable(this.favoriteProducts)
                .filter(favoriteProduct -> favoriteProduct.getProductId().equals(productId))
                .singleOrEmpty();
    }

    @Override
    public Flux<FavoriteProduct> findAll() {
        return Flux.fromIterable(this.favoriteProducts);
    }
}
