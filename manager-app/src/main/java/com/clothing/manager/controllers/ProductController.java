package com.clothing.manager.controllers;

import com.clothing.manager.controllers.payload.UpdateProductPayload;
import com.clothing.manager.models.Product;
import com.clothing.manager.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("atum/products/{productId:\\d+}")
public class ProductController {

    private final ProductService productService;

    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Integer productId) {
        return this.productService.findProduct(productId)
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
                                @Valid UpdateProductPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "atum/products/edit";
        } else {
            this.productService.updateProduct(product.getId(), payload.title(), payload.description());
            return "redirect:/atum/products/%d".formatted(product.getId());
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute(value = "product", binding = false) Product product) {
        this.productService.deleteProduct(product.getId());
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
