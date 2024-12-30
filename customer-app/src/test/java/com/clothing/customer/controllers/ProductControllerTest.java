package com.clothing.customer.controllers;

import com.clothing.customer.client.FavoriteProductsClient;
import com.clothing.customer.client.ProductReviewsClient;
import com.clothing.customer.client.ProductsClient;
import com.clothing.customer.models.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductsClient productsClient;

    @Mock
    FavoriteProductsClient favoriteProductsClient;

    @Mock
    ProductReviewsClient productReviewsClient;

    @InjectMocks
    ProductController controller;

    @Test
    void loadProduct_ProductExists_ReturnsNotEmptyMono() {
        // given
        var product = new Product(1, "Товар №1", "Описание товара №1");
        doReturn(Mono.just(product)).when(this.productsClient).findProduct(1);

        // when
        StepVerifier.create(this.controller.loadProduct(1))
                // then
                .expectNext(new Product(1, "Товар №1", "Описание товара №1"))
                .verifyComplete();

        // Verify можно не вызывать для методов чтения,
        // но обязательно нужно это делать для методов, вносящих изменения
        verify(this.productsClient).findProduct(1);
        verifyNoMoreInteractions(this.productsClient);
        verifyNoInteractions(this.favoriteProductsClient, this.productReviewsClient);
    }

    @Test
    void loadProduct_ProductDoesNotExists_ReturnsMonoWithNoSuchElementsException() {
        // given
        doReturn(Mono.empty()).when(this.productsClient).findProduct(1);

        // when
        StepVerifier.create(this.controller.loadProduct(1))
                // then
                .expectErrorMatches(exception -> exception instanceof NoSuchElementException e &&
                        e.getMessage().equals("customer.products.error.not_found"))
                .verify();

        // Verify можно не вызывать для методов чтения,
        // но обязательно нужно это делать для методов, вносящих изменения
        verify(this.productsClient).findProduct(1);
        verifyNoMoreInteractions(this.productsClient);
        verifyNoInteractions(this.favoriteProductsClient, this.productReviewsClient);
    }

    @Test
    @DisplayName("Exception NoSuchElementException must be added to errors/404 page")
    void handleNoSuchElementException_ReturnsErrors404() {
        // given
        var exception = new NoSuchElementException("Product not found");
        var model = new ConcurrentModel();

        // when
        var result = this.controller.handleNoSuchElementException(exception, model);

        // then
        assertEquals("errors/404", result);
        assertEquals("Product not found", model.getAttribute("error"));
    }
}