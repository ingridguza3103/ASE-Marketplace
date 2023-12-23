package com.example.Marketplace.service;

import com.example.Marketplace.model.User;

import com.example.Marketplace.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private User mockUser;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void tearDown() {
        Mockito.reset(mockUser);
    }

    @Test
    void testGenerateToken() {
        when(mockUser.getUsername()).thenReturn("mockUser");

        String token = tokenService.generateToken(mockUser);

        System.out.println(token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testValidateTokenIsValid() {
        when(mockUser.getUsername()).thenReturn("mockUser");

        String token = tokenService.generateToken(mockUser);

        boolean isValid = tokenService.validateToken(token, mockUser);

        assertTrue(isValid);
    }

    @Test
    void testValidateTokenIsNotValid() {
        User user = new User();
        user.setUsername("NotMockUser");
        user.setPw("pw");

        when(mockUser.getUsername()).thenReturn("mockUser");

        String token = tokenService.generateToken(mockUser);

        boolean isValid = tokenService.validateToken(token, user);

        assertFalse(isValid);
    }
}