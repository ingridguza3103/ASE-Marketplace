package com.example.Marketplace.controller;

import com.example.Marketplace.model.Product;
import com.example.Marketplace.model.User;
import com.example.Marketplace.service.ProductService;
import com.example.Marketplace.service.TokenService;
import com.example.Marketplace.service.UserService;
import exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Enumeration;
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

    @GetMapping("/upload")
    public String showUploadForm(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        Product product = new Product();
        HttpSession session = getSession(getSessionId());
        if (session != null) {
            // Retrieve user information from the session
            Long userId = (Long) session.getAttribute("userId");
            System.out.println("SELLER ID 1: " + userId);
            String username = (String) session.getAttribute("username");
            product.setSellerId(userId);
        }
        model.addAttribute("product", product);
        /*HttpHeaders headers = new HttpHeaders();
        //add all existing headers in new headers
        String result = null;
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if ("Authorization".equalsIgnoreCase(name)) {
                result = request.getHeader(name);
                headers.add("Authorization", result);
            }
        }
        //add the headers to the responseEntity along with yourBody object
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("product_upload"); */
        return "product-upload";
    }

    /**
     * Get mapping to retrieve all products
     *
     * @return the list of all products
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    /**
     * Get mapping to retrieve a product by its id
     *
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
     * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
     *
     * @param product the product to upload
     * @param token   the user token for validation
     * @return ResponseEntity<String> with status depending on success or failure
     */
    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(@RequestBody Product product, @CookieValue("token") String token, @CookieValue("sessionId") String sessionId) {
        // TODO: user authentication
        if (product != null) {
            // extract user from product
            HttpSession session = getSession(sessionId);

            Long userId = (Long) session.getAttribute("userId");
            String userName = (String) session.getAttribute("userName");
            System.out.println("PROD: " + product);

            //User user = userService.getUser(product.getSellerId());
            //System.out.println("SELLER ID 2: " + product.getSellerId());

            System.out.println(String.format("UPLOAD: name: %s, price: %f, quantity: %d, " +
                            "category: %d, picture: %s, description: %s", product.getProductName(),
                    product.getPrice(), product.getQuantity(), product.getCategoryId(),
                    product.getPictureUrl(), product.getDescription()));

            // authenticate the user, if its token is valid, add the product to the db
            if (tokenService.validateToken(token, userService.getUser(userId))) {
                // save the product to the database
                productService.uploadProduct(product);
                // return successful upload of product
                return ResponseEntity.ok().body("upload_success");
            } else {
                // send ResponseEntity unauthorized; the client side knows what to do
                return ResponseEntity.status(401).body("upload_failed_unauthorized");
            }

        } else {
            // send ResponseEntity 400 bad request
            return ResponseEntity.badRequest().body("upload_failed_product_null");
        }
    }

    private HttpSession getSession(String sessionId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getSession(false); // Passing false to create a new session only if one doesn't exist
    }

    private String getSessionId(){
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }
}


/**
 * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
 *
 * @param product the product to upload
 * @param token the user token for validation
 * @param model the model
 * @return ResponseEntity<String> with status depending on success or failure
 * <p>
 * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
 * @param product the product to upload
 * @param token the user token for validation
 * @param model the model
 * @return ResponseEntity<String> with status depending on success or failure
 * <p>
 * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
 * @param product the product to upload
 * @param token the user token for validation
 * @param model the model
 * @return ResponseEntity<String> with status depending on success or failure
 * <p>
 * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
 * @param product the product to upload
 * @param token the user token for validation
 * @param model the model
 * @return ResponseEntity<String> with status depending on success or failure
 * <p>
 * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
 * @param product the product to upload
 * @param token the user token for validation
 * @param model the model
 * @return ResponseEntity<String> with status depending on success or failure
 */


/**
 * Post method to upload a product, performs user authentication and only uploads a product if the token is valid.
 * @param product the product to upload
 * @param token the user token for validation
 * @param model the model
 * @return ResponseEntity<String> with status depending on success or failure
 */
        /*@PostMapping("/products")
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

        } */




