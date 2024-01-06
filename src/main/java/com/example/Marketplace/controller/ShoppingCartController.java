package com.example.Marketplace.controller;

import com.example.Marketplace.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Controller class to handle the REST methods for the shopping-cart
 */
@Controller
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    // TODO: implement!!
}
