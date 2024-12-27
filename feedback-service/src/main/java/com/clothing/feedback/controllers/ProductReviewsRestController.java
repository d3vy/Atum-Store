package com.clothing.feedback.controllers;

import com.clothing.feedback.controllers.payload.NewProductReviewPayload;
import com.clothing.feedback.models.ProductReview;
import com.clothing.feedback.service.ProductReviewsService;
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
@RequestMapping("feedback-api/product-reviews")
public class ProductReviewsRestController {

    private final ProductReviewsService productReviewsService;

    @GetMapping("by-product-id/{productId:\\d+}")
    public Flux<ProductReview> findProductReviewsByProductId(@PathVariable("productId") Integer productId) {
        return this.productReviewsService.findProductReviewsBytProductId(productId);
    }

    @PostMapping
    public Mono<ResponseEntity<ProductReview>> createProductReview(@Valid @RequestBody Mono<NewProductReviewPayload> payloadMono,
                                                                   UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> this.productReviewsService.createProductReview(payload.productId(), payload.rating(), payload.review()))
                .map(productReview -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("/feedback-api/product-reviews/{id}")
                                .build(Map.of("id", productReview.getId())))
                        .body(productReview));
    }
}
