package com.example.Marketplace.service;

import com.example.Marketplace.model.User;

import exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    /**
     * This method is used to perform the login authentication
     * @param user the user to be logged in
     * @return true if login success, return false if login failed
     */
    public boolean loginUser(User user);

    /**
     * This method generates the JWT token for the user and stores it in a cookie and returns the response
     * @param loginUser the user for which the token should be generated
     * @param response response from controller
     * @param responseBody the string that should be added to the response body
     * @return the ResponseEntity after token is created and stored in cookie.
     */
    public ResponseEntity<String> createTokenAndCookie(User loginUser, HttpServletResponse response, String responseBody, HttpSession session);
    /**
     * This method is used to perform the registration authentication
     * @param user the user to be logged in
     * @return true if registration success, return false if registration failed
     */
    boolean registerUser(User user);

    User getUser(Long id);

    List<User> getAllUsers();

    void clearTokenCookie(HttpServletResponse response);
}
