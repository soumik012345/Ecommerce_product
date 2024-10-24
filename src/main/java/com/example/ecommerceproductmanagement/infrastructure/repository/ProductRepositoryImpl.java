package com.example.ecommerceproductmanagement.infrastructure.repository;


import com.example.ecommerceproductmanagement.domain.Product;
import com.example.ecommerceproductmanagement.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositoryImpl extends JpaRepository<Product, Long>, ProductRepository {

    @Override
    default List<Product> findAll() {
        return findAll();
    }

    @Override
    default Optional<Product> findById(Long id) {
        return findById(id);
    }

    @Override
    default Product save(Product product) {
        return save(product);
    }

    @Override
    default void deleteById(Long id) {
        deleteById(id);
    }

    @Override
    default Optional<Product> findByName(String name) {
        return findByName(name);
    }
}