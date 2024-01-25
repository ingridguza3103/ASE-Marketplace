package com.example.Marketplace.controller;

import com.example.Marketplace.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller class to handle the REST methods for the shopping-cart
 */
@Controller
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    // TODO: implement!!

    @GetMapping("/cart")
    public String shoppingCart(){
        return "shopping-cart";
    }

    @PostMapping("cart/add")
    public String addToCart() {
        return "product-multi-vendor";
    }

}
