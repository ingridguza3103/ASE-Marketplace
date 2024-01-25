package com.example.Marketplace.controller;

import com.example.Marketplace.model.Product;
import com.example.Marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    ProductService productService;

    /**
     * This get mapping loads the index page and adds all products to the model so they can be loaded and displayed
     * @param model the model
     * @return index
     */
    @GetMapping("/")
    public String getIndexPage(Model model) {
        // retrieve all products
        List<Product> products = productService.getAllProducts();
        // add the products to the model
        model.addAttribute(products);
        return "index";
    }

}
