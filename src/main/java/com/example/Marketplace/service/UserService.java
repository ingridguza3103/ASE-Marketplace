package com.example.Marketplace.service;

import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class is used to hold the functionality for the LoginController
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;


    public UserService(){}

    /**
     * This method is used to perform the login authentication
     * @param user the user to be logged in
     * @return true if login success, return false if login failed
     */
    public boolean loginUser(User user) {
        System.out.println("Username check: " + user.getUsername().split(",")[0]);
        System.out.println("Pw check: " + user.getPw());

        if (userRepository.checkUserExists(user.getUsername().split(",")[0])) { // if user exists in db

            System.out.println("USER EXISTS");
            // retrieve user from userRepo
            User loginUser = userRepository.findByUserName(user.getUsername().split(",")[0]);

            // authenticate user
            if (passwordEncoder.matches(user.getPw().split(",")[0], loginUser.getPw())) { // pw correct
                System.out.println("PW Correct");
                return true;

            } else { // pw incorrect
                System.out.println("PW Incorrect");
                return false;
            }

        }else{ // user not existing
                System.out.println("USER NOT EXISTING");
                return false;
            }
    }

    /**
     * This method generates the JWT token for the user and stores it in a cookie and returns the response
     * @param loginUser the user for which the token should be generated
     * @param response response from controller
     * @param responseBody the string that should be added to the response body
     * @return the ResponseEntity after token is created and stored in cookie.
     */
    public ResponseEntity<String> createTokenAndCookie(User loginUser, HttpServletResponse response, String responseBody) {


        String userToken = tokenService.generateToken(loginUser);
        // create http header and add token
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + userToken);
        // Include the Location header for redirection
        response.addHeader("Location", "/" + responseBody);
        // Add the token to the response
        //model.addAttribute(userToken);

        // Set cookie
        Cookie cookie = new Cookie("token", userToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // TODO: if input correct route to homepage
        System.out.println(userToken);

        return ResponseEntity.ok().headers(header).body(responseBody);
    }

    /**
     * This method is used to perform the registration authentication
     * @param user the user to be logged in
     * @return true if registration success, return false if registration failed
     */
    public boolean registerUser(User user) {
        if (!userRepository.checkUserExists(user.getUsername())) {
            String encodedPassword = passwordEncoder.encode(user.getPw());
            System.out.println("Insert user " + user.getUsername() + " with pw " + user.getPw());
            user.setPw(encodedPassword);
            userRepository.saveAndFlush(user);

            // user registered return true
            return true;

        } else {
            // user already exists
            System.out.println("User already exists");
            return false;
        }
    }
}
