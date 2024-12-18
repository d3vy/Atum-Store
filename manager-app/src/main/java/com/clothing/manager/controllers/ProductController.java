package com.clothing.manager.controllers;

import com.clothing.manager.client.ProductsRestClient;
import com.clothing.manager.controllers.payload.UpdateProductPayload;
import com.clothing.manager.client.BadRequestException;
import com.clothing.manager.models.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("atum/products/{productId:\\d+}")
public class ProductController {

    private final ProductsRestClient productsRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Integer productId) {
        return this.productsRestClient.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("atum.errors.product.not_found"));
    }

    @GetMapping
    public String getProduct() {
        return "atum/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage() {
        return "atum/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute(value = "product", binding = false) Product product,
                                UpdateProductPayload payload,
                                Model model) {
        try {
            this.productsRestClient.updateProduct(product.id(), payload.title(), payload.description());
            return "redirect:/atum/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getMessage());
            return "atum/products/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute(value = "product", binding = false) Product product) {
        this.productsRestClient.deleteProduct(product.id());
        return "redirect:/atum/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response,
                                               Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
