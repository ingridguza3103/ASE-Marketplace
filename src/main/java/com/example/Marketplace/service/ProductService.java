package com.example.Marketplace.service;

import com.example.Marketplace.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(long id);

    boolean uploadProduct(Product product);

    void removeProduct(Product product);


}
