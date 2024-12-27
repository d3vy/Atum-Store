package com.clothing.customer.controllers.payload;

public record NewProductReviewPayload(
        Integer rating,
        String review) {
}
