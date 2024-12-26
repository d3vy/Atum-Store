package com.clothing.customer.controllers;

import com.clothing.customer.client.ProductsClient;
import com.clothing.customer.controllers.payload.NewProductReviewPayload;
import com.clothing.customer.models.Product;
import com.clothing.customer.service.FavoriteProductsService;
import com.clothing.customer.service.ProductReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;
    private final FavoriteProductsService favoriteProductsService;
    private final ProductReviewsService productReviewsService;

    @ModelAttribute(value = "product", binding = false)
    public Mono<Product> loadProduct(@PathVariable("productId") Integer productId) {
        return productsClient.findProduct(productId);
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") Integer productId, Model model) {
        model.addAttribute("inFavorites", false);
        return this.productReviewsService.findProductReviewsBytProductId(productId)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(this.favoriteProductsService.findFavoriteProductByProductId(productId)
                        .doOnNext(_ -> model.addAttribute("inFavorites", true)))
                .thenReturn("customer/products/product");
    }

    @PostMapping("add-to-favorites")
    public Mono<String> addProductToFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favoriteProductsService.addProductToFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("remove-from-favorites")
    public Mono<String> removeProductFromFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> this.favoriteProductsService.removeProductFromFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@ModelAttribute("product") Mono<Product> productMono,
                                     @Valid NewProductReviewPayload payload,
                                     BindingResult bindingResult,
                                     Model model) {
        if ((bindingResult.hasErrors())) {
            model.addAttribute("isFavorite", false);
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return productMono
                    .map(Product::id)
                    .flatMap(productId -> this.favoriteProductsService.findFavoriteProductByProductId(productId)
                            .doOnNext(_ -> model.addAttribute("inFavorites", true)))
                    .thenReturn("customer/products/product");
        } else {
            return productMono
                    .map(Product::id)
                    .flatMap(productId -> this.productReviewsService.createProductReview(productId, payload.rating(), payload.review())
                            .thenReturn("redirect:/customer/products/%d".formatted(productId)));
        }
    }
}
