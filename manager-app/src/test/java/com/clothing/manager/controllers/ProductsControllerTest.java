package com.clothing.manager.controllers;

import com.clothing.manager.client.BadRequestException;
import com.clothing.manager.client.ProductsRestClient;
import com.clothing.manager.controllers.payload.NewProductPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

//@ExtendWith(MockitoExtension.class)
//@DisplayName("Module test ProductsController")
//class ProductsControllerTest {
//
//    @Mock
//    ProductsRestClient productsRestClient;
//
//    @InjectMocks
//    ProductsController controller;
//
//    // Каждый метод должен тестировать одну функцию, не опираясь на что-либо другое. К примеру, в createProduct я проверь на null и any,
//    // вместо того, чтобы передавать payload.title() и payload.description().
//    @Test
//    @DisplayName("create product returns product form with errors if request is invalid")
//    void createProduct_RequestIsInvalid_ReturnsProductFormWithErrors() {
//        // given
//        var payload = new NewProductPayload("   ", null);
//        var model = new ConcurrentModel();
//
//        Mockito.doThrow(new BadRequestException(List.of("Error 1", "Error 2")))
//                .when(this.productsRestClient)
//                .createProduct("   ", null);
//
//        // when
//        var result = this.controller.createProduct(payload, model);
//
//        // then
//        Assertions.assertEquals("atum/products/new_product", result);
//        Assertions.assertEquals(payload, model.getAttribute("payload"));
//        Assertions.assertEquals(List.of("Error 1", "Error 2"), model.getAttribute("errors"));
//
//        // Verify that createProduct was called once with invalid parameters
//        Mockito.verify(this.productsRestClient).createProduct("   ", null);
//
//        // Verify no further interactions occurred
//        Mockito.verifyNoMoreInteractions(this.productsRestClient);
//    }
//}