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


@Service
public class TokenServiceImpl implements TokenService {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);;
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

    @Override
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
