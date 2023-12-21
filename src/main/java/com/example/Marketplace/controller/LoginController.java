package com.example.Marketplace.controller;

import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;

import com.example.Marketplace.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

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
        // Simple placeholder for now
        // Implement authentication logic here
        // TODO: check if user exists
        System.out.println("Username check: " + user.getUsername().split(",")[0]);
        System.out.println("Pw check: " + user.getPw());
        if (userRepository.checkUserExists(user.getUsername().split(",")[0])) {

            System.out.println("USER EXISTS");
            // retrieve user from userRepo
            User loginUser = userRepository.findByUserName(user.getUsername().split(",")[0]);

            // authenticate user
            if (passwordEncoder.matches(user.getPw().split(",")[0], loginUser.getPw())) { // pw correct
                System.out.println("PW Correct");

                // generate JWT token
                String userToken = tokenService.generateToken(loginUser);
                // create http header and add token
                HttpHeaders header = new HttpHeaders();
                header.add("Authorization", "Bearer " + userToken);
                // Include the Location header for redirection
                response.addHeader("Location", "/login_success");
                // Add the token to the response
                //model.addAttribute(userToken);

                // Set cookie
                Cookie cookie = new Cookie("token", userToken);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);

                // TODO: if input correct route to homepage
                System.out.println(userToken);
                return ResponseEntity.ok().headers(header).body("login_success");
            } else { // pw incorrect
                System.out.println("PW Incorrect");
                // TODO: print username or password incorrect if incorrect input
                model.addAttribute("loginError", "Username or password incorrect!");
                return ResponseEntity.status(401).body("login_failed");
            }



        } else {
            // TODO: PRINT user does not exist
            System.out.println("USER NOT EXISTING");
            model.addAttribute("loginError", "User does not exist");
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
        if (user.getUsername().contains(",") && user.getUsername().contains(",")) {
            user.setUsername(user.getUsername().split(",")[1]);
            user.setPw(user.getPw().split(",")[1]);
        }
        if (!userRepository.checkUserExists(user.getUsername())) {
            String encodedPassword = passwordEncoder.encode(user.getPw());
            System.out.println("Insert user " + user.getUsername() + " with pw " + user.getPw());
            user.setPw(encodedPassword);
            userRepository.saveAndFlush(user);

            // TODO: refactor
            // generate JWT token and store it in a cookie
            String userToken = tokenService.generateToken(user);

            HttpHeaders header = new HttpHeaders();
            header.add("Authorization", "Bearer " + userToken);
            // Include the Location header for redirection
            response.addHeader("Location", "/registration_success");
            // Add the token to the response
            //model.addAttribute(userToken);

            // Set cookie
            Cookie cookie = new Cookie("token", userToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);


            return ResponseEntity.ok().headers(header).body("registration_success");
        } else {
            // TODO: Print username already exists to the model
            model.addAttribute("loginError", "Username already exists");
            return ResponseEntity.status(401).body("registration_failed"); // Return to registration form with error message
        }


    }


}