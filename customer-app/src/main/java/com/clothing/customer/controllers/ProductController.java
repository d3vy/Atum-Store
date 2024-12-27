package com.clothing.customer.controllers;

import com.clothing.customer.client.FavoriteProductsClient;
import com.clothing.customer.client.ProductReviewsClient;
import com.clothing.customer.client.ProductsClient;
import com.clothing.customer.controllers.payload.NewProductReviewPayload;
import com.clothing.customer.exception.ClientBadRequestException;
import com.clothing.customer.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;
    private final FavoriteProductsClient favoriteProductsClient;
    private final ProductReviewsClient productReviewsClient;

    @ModelAttribute(value = "product", binding = false)
    public Mono<Product> loadProduct(@PathVariable("productId") Integer productId) {
        return productsClient.findProduct(productId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("customer.products.error.not_found")));
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") Integer productId, Model model) {
        model.addAttribute("inFavorites", false);
        return this.productReviewsClient.findProductReviewsByProductId(productId)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(this.favoriteProductsClient.findFavoriteProductByProductId(productId)
                        .doOnNext(_ -> model.addAttribute("inFavorites", true)))
                .thenReturn("customer/products/product");
    }

    @PostMapping("add-to-favorites")
    public Mono<String> addProductToFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favoriteProductsClient.addProductToFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))
                        .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just("redirect:/customer/products/%d".formatted(productId));
                        }));
    }

    @PostMapping("remove-from-favorites")
    public Mono<String> removeProductFromFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favoriteProductsClient.removeProductFromFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    // Решить вопрос с productId
    @PostMapping("create-review")
    public Mono<String> createReview(@ModelAttribute("product") Mono<Product> productMono,
                                     NewProductReviewPayload payload,
                                     Model model) {
        return productMono
                .map(Product::id)
                .flatMap(productId1 -> this.productReviewsClient.createProductReview(productId1, payload.rating(), payload.review())
                        .thenReturn("redirect:/customer/products/%d".formatted(productId1))
                        .onErrorResume(ClientBadRequestException.class,
                                exception -> {
                                    model.addAttribute("isFavorite", false);
                                    model.addAttribute("payload", payload);
                                    model.addAttribute("errors", exception.getErrors());
                                    return productMono
                                            .map(Product::id)
                                            .flatMap(productId2 -> this.favoriteProductsClient.findFavoriteProductByProductId(productId2)
                                                    .doOnNext(_ -> model.addAttribute("inFavorites", true)))
                                            .thenReturn("customer/products/product");
                                }));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }
}
