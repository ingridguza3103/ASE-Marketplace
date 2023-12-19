package com.example.Marketplace.service;

import com.example.Marketplace.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenService {
    private static final String SECRET_KEY = "secret";

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*3600*12)) //set expiration time of token to 12 hours
                .signWith(SignatureAlgorithm.ES256, SECRET_KEY)
                .compact();
    }


    public boolean validateToken(String token, User user) {
        //extract username from token
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isExpired(token));
    }

    private String getUsernameFromToken(String token) {
        // parse subject from token
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isExpired(String token) {
        // parse expiration before token
        Date expiration = Jwts.parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(token)
                            .getBody()
                            .getExpiration();

        return expiration.before(new Date());
    }



}
