package com.example.Marketplace.controller;

import com.example.Marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
}
