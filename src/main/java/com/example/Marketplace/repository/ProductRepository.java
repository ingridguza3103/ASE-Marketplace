package com.example.Marketplace.repository;

import com.example.Marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT EXISTS(SELECT p FROM Product p WHERE p.id = :id)")
    boolean checkProductExists(Long id);
}
