package com.example.Marketplace.controller;

import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;

import com.example.Marketplace.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This class handles all REST calls regarding the login
 */
@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    private UserService userService;

    /**
     * GET request handler for login loads the login form
     * @param model the model
     * @return index.html
     */
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "my-account";
    }

    @GetMapping("/login_success")
    public String showLoginSuccessPage() {
        return "login_success"; // Thymeleaf will resolve this to "templates/login_success.html"
    }

    /**
     * This method handles all POST requests and performs user authentication
     * @param user the User object created from username and password from the login form
     * @param model the model
     * @return index.html if login failed, login_success.html if login succeeded
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@ModelAttribute  User user, Model model, HttpServletResponse response) {
        if (user.getUsername().contains(",") && user.getPw().contains(",")) {
            user.setUsername(user.getUsername().split(",")[0]);
            user.setPw(user.getPw().split(",")[0]);
        }

        // Implement authentication logic here
        boolean isLoggedIn = userService.loginUser(user);

        if (isLoggedIn) {
            // generate Token and add it in cookie to response
            return userService.createTokenAndCookie(user, response, "login_success");
        } else {
            return ResponseEntity.status(401).body("login_failed");
        }

    }


    @GetMapping("/registration_success")
    public String showRegisterSuccessPage() {
        return "registration_success"; // Thymeleaf will resolve this to "templates/login_success.html"
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(User user, Model model, HttpServletResponse response) {
        // check if user already exists and only save if not
        if (user.getUsername().contains(",") && user.getPw().contains(",")) {
            user.setUsername(user.getUsername().split(",")[1]);
            user.setPw(user.getPw().split(",")[1]);
        }

        boolean isRegistered = userService.registerUser(user);

        if (isRegistered) {
            // create token and cookie and add it to the response
            return userService.createTokenAndCookie(user, response, "registration_success");
        } else {
            return ResponseEntity.status(401).body("registration_failed"); // Return to registration form with error message
        }


    }


}