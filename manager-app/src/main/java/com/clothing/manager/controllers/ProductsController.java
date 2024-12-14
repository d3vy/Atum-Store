package com.clothing.manager.controllers;

import com.clothing.manager.client.ProductsRestClient;
import com.clothing.manager.controllers.payload.NewProductPayload;
import com.clothing.manager.client.BadRequestException;
import com.clothing.manager.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("atum/products")
public class ProductsController {

    private final ProductsRestClient productsRestClient;

    @GetMapping("list")
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productsRestClient.findAllProducts());
        return "atum/products/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "atum/products/new_product";
    }

    @PostMapping("create")
    public String createProduct(NewProductPayload payload,
                                Model model) {
        try {
            Product product = this.productsRestClient.createProduct(payload.title(), payload.description());
            return "redirect:/atum/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getMessage());
            return "atum/products/new_product";
        }


    }
}