package com.clothing.atum.controllers;

import com.clothing.atum.controllers.payload.UpdateProductPayload;
import com.clothing.atum.models.Product;
import com.clothing.atum.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("atum-api/products/{productId:\\d+}")
public class ProductRestController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") Integer productId) {
        return productService.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("atum.errors.product.not_found"));
    }

    @GetMapping
    public Product findProduct(@ModelAttribute("product") Product product) {
        return product;
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(@ModelAttribute("product") Product product,
                                           @Valid @RequestBody UpdateProductPayload payload,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.productService.updateProduct(product.getId(), payload.title(), payload.description());
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product.getId());
        return ResponseEntity.noContent()
                .build();

    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(
                                exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
