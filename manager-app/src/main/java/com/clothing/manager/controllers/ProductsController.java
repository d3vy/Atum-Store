package com.clothing.manager.controllers;

import com.clothing.manager.controllers.payload.NewProductPayload;
import com.clothing.manager.models.Product;
import com.clothing.manager.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("atum/products")
public class ProductsController {

    private final ProductService productService;

    @GetMapping("list")
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productService.findAllProducts());
        return "atum/products/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "atum/products/new_product";
    }

    @PostMapping("create")
    public String createProduct(@Valid NewProductPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "atum/products/new_product";
        } else {
            Product product = this.productService.createProduct(payload.title(), payload.description());
            return "redirect:/atum/products/%d".formatted(product.getId());
        }
    }
}