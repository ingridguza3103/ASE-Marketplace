package com.example.Marketplace.service;

import com.example.Marketplace.model.User;

public interface TokenService {
    String generateToken(User user);
    boolean validateToken(String token, User user);
}
