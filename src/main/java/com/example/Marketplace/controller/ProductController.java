package com.example.Marketplace.controller;

import com.example.Marketplace.model.Product;
import com.example.Marketplace.model.User;
import com.example.Marketplace.service.ProductService;
import com.example.Marketplace.service.TokenService;
import com.example.Marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * This class represents the REST endpoint for products
 */
@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;
    // TODO: implement!!


    /**
     * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
     * @param product the product to upload
     * @param token the user token for validation
     * @param model the model
     * @return ResponseEntity<String> with status depending on success or failure
     */
    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@ModelAttribute Product product, @RequestHeader("Authorization") String token, Model model) {
        // TODO: user authentication
        if (product != null) {
            // extract user from product
            User user = userService.getUser(product.getSellerId());

            // split user token to get rid of "Bearer"
            String userToken = token.split(" ")[1];

            // authenticate the user, if it token valid add product to db
            if (tokenService.validateToken(userToken, user)) {
                // save the product to the database
                productService.uploadProduct(product);
                // return successful upload of product
                return ResponseEntity.ok().body("upload_success");
            } else {
                // send ResponseEntity unauthorized the client side knows what to do
                return ResponseEntity.status(401).body("upload_failed_unauthorized");
            }

        } else {
            // send ResponseEntity 400 bad request
            return ResponseEntity.badRequest().body("upload_failed_product_null");
        }

    }

}
