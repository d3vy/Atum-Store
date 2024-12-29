package com.clothing.feedback.service;

import com.clothing.feedback.models.FavoriteProduct;
import com.clothing.feedback.repositories.FavoriteProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FavoriteProductsServiceImpl implements FavoriteProductsService {

    private final FavoriteProductRepository favoriteProductRepository;

    @Override
    public Mono<FavoriteProduct> addProductToFavorites(Integer productId) {
        return this.favoriteProductRepository.save(new FavoriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(Integer productId) {
        return this.favoriteProductRepository.deleteByProductId(productId);
    }

    @Override
    public Mono<FavoriteProduct> findFavoriteProductByProductId(Integer productId) {
        return this.favoriteProductRepository.findByProductId(productId);
    }

    @Override
    public Flux<FavoriteProduct> findAllFavoriteProducts() {
        return this.favoriteProductRepository.findAll();
    }
}