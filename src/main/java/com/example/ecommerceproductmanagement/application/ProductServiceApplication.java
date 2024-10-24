package com.example.ecommerceproductmanagement.application;

import com.example.ecommerceproductmanagement.domain.Discount;
import com.example.ecommerceproductmanagement.domain.Product;
import com.example.ecommerceproductmanagement.domain.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductServiceApplication {

    private final ProductService productService;

    @Autowired
    public ProductServiceApplication(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort) {
        Page<Product> productPage = productService.findAll(page, size, sort);
        return ResponseEntity.ok(productPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getCategory());
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/update-stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        productService.updateStockQuantity(id, quantity);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/apply-discount")
    public ResponseEntity<Void> applyDiscount(
            @PathVariable Long id,
            @RequestParam BigDecimal discountValue,
            @RequestParam Discount.DiscountType discountType) {

        productService.applyDiscount(id, discountValue, discountType);
        return ResponseEntity.noContent().build();
    }
}

