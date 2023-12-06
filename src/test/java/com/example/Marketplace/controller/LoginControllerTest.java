package com.example.Marketplace.controller;


import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private User mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new User();
        mockUser.setUsername("mockUser");
        mockUser.setPw(passwordEncoder.encode("mockPassword"));
    }

    @AfterEach
    public void tearDown() {
        mockUser = null;
    }

    @Test
    public void testLoginPage() throws Exception {
        // mock get operation to see if index page works
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        String username = "mockUser";
        String correctPw = "mockPassword";
        // mock userRepository to return true simulating that the user exists in the db
        when(userRepository.checkUserExists(username)).thenReturn(true);
        // mock userRepository user object return
        when(userRepository.findByUserName(username)).thenReturn(mockUser);
        // mock password input correct
        when(passwordEncoder.matches(correctPw, mockUser.getPw())).thenReturn(true);
        // mock post request to login page and simulate correct user login
        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("pw", correctPw))
                .andExpect(status().isOk())
                .andExpect(view().name("login_success"));
    }

    @Test
    public void testIncorrectPassword() throws Exception {
        String username = "mockUser";
        String wrongPw = "notMockPassword";
        // mock userRepository to return true simulating that the user exists in the db
        when(userRepository.checkUserExists(username)).thenReturn(true);

        // mock userRepository user object return
        when(userRepository.findByUserName(username)).thenReturn(mockUser);
        // mock password input incorrect
        when(passwordEncoder.matches(wrongPw, mockUser.getPw())).thenReturn(false);
        // mock post request to login page to simulate incorrect login
        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("pw", wrongPw))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("loginError"));
    }

    @Test
    public void userNotExistingTest() throws Exception {
        String username = "mockUser";
        String password = "password";

        // mock userRepository to return false for checkUserExists simulating user not in db
        when(userRepository.checkUserExists(username)).thenReturn(false);
        // mock post request to login page and simulate user login with non-existing username
        mockMvc.perform(post("/login")
                .param("username", username)
                .param("pw", password))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("loginError"));
    }


}