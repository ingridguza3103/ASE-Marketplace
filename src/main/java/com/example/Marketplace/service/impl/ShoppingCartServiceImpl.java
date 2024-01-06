package com.example.Marketplace.service.impl;

import com.example.Marketplace.repository.ShoppingCartRepository;
import com.example.Marketplace.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    // TODO: implement!!
}
