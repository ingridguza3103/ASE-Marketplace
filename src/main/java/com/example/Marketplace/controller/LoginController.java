package com.example.Marketplace.controller;

import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * This method handles all POST requests and performs user authentication
     * @param user the User object created from username and password from the login form
     * @param model the model
     * @return index.html if login failed, login_success.html if login succeeded
     */
    @PostMapping("/login")
    public String login(@ModelAttribute  User user, Model model) {
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
                // TODO: if input correct route to homepage
                return "login_success";
            } else { // pw incorrect
                System.out.println("PW Incorrect");
                // TODO: print username or password incorrect if incorrect input
                model.addAttribute("loginError", "Username or password incorrect!");
                return "my-account";
            }



        } else {
            // TODO: PRINT user does not exist
            System.out.println("USER NOT EXISTING");
            model.addAttribute("loginError", "User does not exist");
            return "my-account";

        }



    }



    @PostMapping("/register")
    public String registerUser(User user, Model model) {
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
            return "registration_success";
        } else {
            // TODO: Print username already exists to the model
            model.addAttribute("loginError", "Username already exists");
            return "my-account"; // Return to registration form with error message
        }


    }


}