package com.example.Marketplace.repository;

import com.example.Marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository class for SQL queries in the products table
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * This method queries the products database and checks if a product exists
     * @param id the product id
     * @return true if exists, false otherwise
     */
    @Query("SELECT EXISTS(SELECT p FROM Product p WHERE p.id = :id)")
    boolean checkProductExists(Long id);
}
