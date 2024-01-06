package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.User;

import com.example.Marketplace.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to generate and validat JWT tokens
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);;

    /**
     * This method is used to generate a JWT token for a specific user upon successful login or registration
     * @param user
     * @return
     */
    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        // print key just for verification purpose on jwt.io
        System.out.println("JWT KEY: " + io.jsonwebtoken.io.Encoders.BASE64.encode(SECRET_KEY.getEncoded()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*3600*12)) //set expiration time of token to 12 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * This method is used to validate a user token
     * @param token the token to validate
     * @param user the user to validate it against
     * @return true if valid, false otherwise
     */
    @Override
    public boolean validateToken(String token, User user) {
        //extract username from token
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isExpired(token));
    }


    /**
     * This method is used to extract the username from the token it is only used by validateToken
     * @param token the token
     * @return the extracted username
     */
    private String getUsernameFromToken(String token) {
        // parse subject from token
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * This method extracts the expiration date from the token and checks if it has expired.
     * This method is also only used by validateToken.
     * @param token the token
     * @return true if expired, false otherwise
     */
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
