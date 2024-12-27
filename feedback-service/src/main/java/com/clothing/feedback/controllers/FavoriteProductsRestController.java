package com.clothing.feedback.controllers;

import com.clothing.feedback.controllers.payload.NewFavoriteProductPayload;
import com.clothing.feedback.models.FavoriteProduct;
import com.clothing.feedback.service.FavoriteProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback-api/favorite-products")
public class FavoriteProductsRestController {

    private final FavoriteProductsService favoriteProductsService;

    @GetMapping
    public Flux<FavoriteProduct> findFavoriteProducts() {
        return this.favoriteProductsService.findAllFavoriteProducts();
    }

    @GetMapping("by-product-id/{productId:\\d+}")
    public Mono<FavoriteProduct> findFavoriteProductByProductId(@PathVariable("productId") Integer productId) {
        return this.favoriteProductsService.findFavoriteProductByProductId(productId);
    }

    @PostMapping
    public Mono<ResponseEntity<FavoriteProduct>> addProductToFavorites(@Valid @RequestBody Mono<NewFavoriteProductPayload> payloadMono,
                                                                       UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> this.favoriteProductsService.addProductToFavorites(payload.productId()))
                .map(favoriteProduct -> ResponseEntity.created(uriComponentsBuilder.replacePath("feedback-api/favorite-products/{id}")
                                .build(Map.of("id", favoriteProduct.getId())))
                        .body(favoriteProduct));

    }

    @DeleteMapping("by-product-id/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavorites(@PathVariable("productId") Integer productId) {
        return this.favoriteProductsService.removeProductFromFavorites(productId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
