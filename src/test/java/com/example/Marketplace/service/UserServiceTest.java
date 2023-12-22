package com.example.Marketplace.service;

import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;

import com.example.Marketplace.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setUsername("mockUser");
        mockUser.setPw("mockPassword");
    }

    @AfterEach
    void tearDown(){
        Mockito.reset(userRepository, passwordEncoder, tokenService);
        mockUser = null;
    }

    @Test
    void testLoginUserSuccess() {
        // Mocking
        when(userRepository.checkUserExists("mockUser")).thenReturn(true);
        when(userRepository.findByUserName("mockUser")).thenReturn(mockUser);
        when(passwordEncoder.matches("mockPassword", mockUser.getPw())).thenReturn(true);

        // Test
        boolean isLoggedIn = userService.loginUser(mockUser);

        // Verify
        assertTrue(isLoggedIn);
    }

    @Test
    void testLoginUserUserNotExisting() {
        // Mocking
        when(userRepository.checkUserExists("mockUser")).thenReturn(false);

        // Test
        boolean isLoggedIn = userService.loginUser(mockUser);

        // Verify
        assertFalse(isLoggedIn);
    }

    @Test
    void testLoginUserWrongPw() {
        // Mocking
        when(userRepository.checkUserExists("mockUser")).thenReturn(true);
        when(userRepository.findByUserName("mockUser")).thenReturn(mockUser);
        when(passwordEncoder.matches("mockPassword", mockUser.getPw())).thenReturn(false);

        // Test
        boolean isLoggedIn = userService.loginUser(mockUser);

        // Verify
        assertFalse(isLoggedIn);
    }


    @Test
    void testRegisterUserSuccess() {
        // Mocking
        when(userRepository.checkUserExists("mockUser")).thenReturn(false);

        // Test
        boolean isRegistered = userService.registerUser(mockUser);

        // Verify
        assertTrue(isRegistered);
    }

    @Test
    void testRegisterUserFailed() {
        // Mocking
        when(userRepository.checkUserExists("mockUser")).thenReturn(true);


        // Test
        boolean isRegistered = userService.registerUser(mockUser);

        // Verify
        assertFalse(isRegistered);
    }

    @Test
    void testCreateTokenAndCookie() {
        String token = "mockToken";
        String bodyMessage = "test";

        when(tokenService.generateToken(mockUser)).thenReturn("mockToken");

        // expected ResponseEntity object
        ResponseEntity<String> expectedResponse = ResponseEntity
                .ok()
                .header("Authorization", "Bearer " + token)
                .body(bodyMessage);

        // test
        ResponseEntity<String> responseEntity = userService.createTokenAndCookie(mockUser, response, bodyMessage);

        assertEquals(expectedResponse, responseEntity, "ResponseEntity check");

    }

}
