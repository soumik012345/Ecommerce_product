package com.example.ecommerceproductmanagement.domain;


import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(String name, String description, BigDecimal price, Integer stockQuantity, String category) {
        validateProduct(name);
        Product product = new Product(name, description, price, stockQuantity, category);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, String name, String description, BigDecimal price, Integer stockQuantity, String category) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateStockQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        product.setStockQuantity(quantity);
        productRepository.save(product);
    }


    private void validateProduct(String name) {

        if (productRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Product name already exists");
        }
    }


    public Page<Product> findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return productRepository.findAll(pageable);
    }


    public void applyDiscount(Long productId, BigDecimal discountValue, Discount.DiscountType discountType) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Discount discount = new Discount(discountValue, discountType);

        // Apply discount to the product's price
        BigDecimal discountedPrice = discount.apply(product.getPrice());
        product.setPrice(discountedPrice);

        productRepository.save(product);
    }

}

