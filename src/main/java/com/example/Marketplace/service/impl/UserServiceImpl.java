package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;
import com.example.Marketplace.service.TokenService;
import com.example.Marketplace.service.UserService;
import exception.ResourceNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * This class is used to hold the functionality for the LoginController
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;


    public UserServiceImpl(){}

    /**
     * This method is used to perform the login authentication
     * @param user the user to be logged in
     * @return true if login success, return false if login failed
     */
    @Override
    public boolean loginUser(User user) {
        System.out.println("Username check: " + user.getUsername());
        System.out.println("Pw check: " + user.getPw());

        if (userRepository.checkUserExists(user.getUsername())) { // if user exists in db

            System.out.println("USER EXISTS");
            // retrieve user from userRepo
            User loginUser = userRepository.findByUserName(user.getUsername());

            // authenticate user
            if (passwordEncoder.matches(user.getPw(), loginUser.getPw())) { // pw correct
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
    @Override
    public ResponseEntity<String> createTokenAndCookie(User loginUser, HttpServletResponse response, String responseBody, HttpSession session) {


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

        // create session cookie

        // Generate a session identifier
        String sessionIdentifier = UUID.randomUUID().toString();
        // Store user information in the session (you may store more user-related data)
        session.setAttribute("userId", loginUser.getId());
        session.setAttribute("username", loginUser.getUsername());

        // Set the session identifier as a cookie in the response
        Cookie sessionCookie = new Cookie("sessionId", sessionIdentifier);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);


        // TODO: if input correct route to homepage
        System.out.println(userToken);

        return ResponseEntity.ok().headers(header).body(responseBody);
    }

    /**
     * This method invalidates the token and the session
     * @param response the response
     */
    @Override
    public void clearTokenCookie(HttpServletResponse response) {
        // clear the token cookie
        Cookie cookie = new Cookie("token", null); // set cookie value to null
        cookie.setMaxAge(0); // set the cookie's max age to 0, effectively deleting it
        cookie.setPath("/");
        response.addCookie(cookie);
        // clear the session cookie
        Cookie sessioncookie = new Cookie("sessionId", null); // set cookie value to null
        sessioncookie.setMaxAge(0); // set the cookie's max age to 0, effectively deleting it
        sessioncookie.setPath("/");
        response.addCookie(sessioncookie);
    }

    /**
     * This method is used to perform the registration authentication
     * @param user the user to be logged in
     * @return true if registration success, return false if registration failed
     */
    @Override
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
    @Override
    public User getUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
