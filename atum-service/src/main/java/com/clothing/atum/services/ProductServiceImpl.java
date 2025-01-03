package com.clothing.atum.services;

import com.clothing.atum.models.Product;
import com.clothing.atum.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.productRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return this.productRepository.findAll();
        }
    }

    @Override
    public Product createProduct(String title, String description) {
        return this.productRepository.save(new Product(null, title, description));
    }

    @Override
    public Optional<Product> findProduct(Integer productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(Integer id, String title, String description) {
        this.productRepository.findById(id).ifPresentOrElse(product -> {
                    product.setTitle(title);
                    product.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                }
        );
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        this.productRepository.deleteById(id);
    }
}
