package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.Product;
import com.example.Marketplace.repository.ProductRepository;
import com.example.Marketplace.service.ProductService;
import exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public boolean uploadProduct(Product product) {
        if (!productRepository.checkProductExists(product.getId())) {
            productRepository.saveAndFlush(product);
            return true;
        }
        return false;
    }
}
