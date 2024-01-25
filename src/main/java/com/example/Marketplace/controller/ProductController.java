package com.example.Marketplace.controller;

import com.example.Marketplace.model.Product;
import com.example.Marketplace.model.User;
import com.example.Marketplace.service.ProductService;
import com.example.Marketplace.service.TokenService;
import com.example.Marketplace.service.UserService;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/product")
    public String getProduct() {
        return "product-multi-vendor";
    }

    /**
     * Get mapping to retrieve all products
     * @return the list of all products
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    /**
     * Get mapping to retrieve a product by its id
     * @param productId the product id
     * @return ResponseEntity<Product> if product exists, ResponseEntity<String> else
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId) {
        Long id = Long.parseLong(productId);
        Product product;
        try {
            product = productService.getProductById(id);

            return ResponseEntity.ok().body(product);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().body("product_not_found");
        }
    }

    /**
     * Get mapping to retrieve all products uploaded by a user
     * @param sellerId the user id
     * @return ResponseEntity<List<Product>> if user has products, ResponseEntity<String> else
     */
    @GetMapping("/products/{sellerId}")
    public ResponseEntity<?> getUserProducts(@PathVariable String sellerId) {
        Long id = Long.parseLong(sellerId);
        ArrayList<Product> userProducts = (ArrayList<Product>) productService.getProductsBySellerId(id);
        if (!userProducts.isEmpty()) {
            return ResponseEntity.ok().body(userProducts);
        } else {
            return ResponseEntity.badRequest().body("no_products_for_userId");
        }

    }

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
