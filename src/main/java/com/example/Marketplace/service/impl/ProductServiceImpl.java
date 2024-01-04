package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.Product;
import com.example.Marketplace.repository.ProductRepository;
import com.example.Marketplace.service.ProductService;
import exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used for calling the SQL queries from ProductRepository
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;


    /**
     * fetch all products from the database
     * @return the list of all products
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * fetch a specific product by id from the db
     * @param id of the product
     * @return the product if it exists
     */
    @Override
    public Product getProductById(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    /**
     * This method is used to save a product in th db if a user uploads one
     * @param product the product to save
     * @return true indicating success, false indicating failure
     */
    @Override
    public boolean uploadProduct(Product product) {
        if (!productRepository.checkProductExists(product.getId())) {
            productRepository.saveAndFlush(product);
            return true;
        }
        return false;
    }

    /**
     * removes a product from the db
     * @param product the product to delete
     */
    @Override
    public void removeProduct(Product product) {
        if (productRepository.checkProductExists(product.getId())) {
            productRepository.delete(product);
        }

    }

    @Override
    public boolean enoughQuantity(Long id, int quantity) {
        return productRepository.availableInDesiredQty(id, quantity);
    }
}
