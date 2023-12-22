package com.example.Marketplace.controller;


import com.example.Marketplace.model.User;
import com.example.Marketplace.repository.UserRepository;

import com.example.Marketplace.service.TokenService;
import com.example.Marketplace.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

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
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-account"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testLoginSuccessPage() throws Exception {
        // mock get operation to see if index page works
        mockMvc.perform(get("/login_success"))
                .andExpect(status().isOk())
                .andExpect(view().name("login_success"));
    }
    @Test
    public void testRegistrationSuccessPage() throws Exception {
        // mock get operation to see if index page works
        mockMvc.perform(get("/registration_success"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration_success"));
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        String username = "mockUser";
        String correctPw = "mockPassword";
        String mockToken = tokenService.generateToken(mockUser);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + mockToken);


        // Mock userService to return true indicating successful login
        when(userService.loginUser(any(User.class))).thenReturn(true);
        when(userService.createTokenAndCookie(any(User.class), any(HttpServletResponse.class), anyString()))
                .thenReturn(ResponseEntity.ok().headers(header).body("login_success"));

        // Perform the login test
        MvcResult result = mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("pw", correctPw))
                .andExpect(status().isOk())
                .andReturn();

        // Extract token and response
        String token = result.getResponse().getHeader("Authorization");
        String response = result.getResponse().getContentAsString();

        // Assertions
        assertNotNull(token, "Token check");
        assertEquals("login_success", response, "Response message check");

    }

    @Test
    public void testLoginFailure() throws Exception {
        String username = "mockUser";
        String correctPw = "mockPassword";


        // Mock userService to return true indicating successful login
        when(userService.loginUser(any(User.class))).thenReturn(false);


        // Perform the login test
        MvcResult result = mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("pw", correctPw))
                .andExpect(status().is(401))
                .andReturn();

        // Extract token and response
        String response = result.getResponse().getContentAsString();

        assertEquals("login_failed", response, "Response message check");

    }

  /*  @Test
    // TODO: test this in UserServiceTest
    public void testIncorrectPassword() throws Exception {
        String username = "mockUser";
        String wrongPw = "notMockPassword";
        // mock userRepository to return true simulating that the user exists in the db
        when(userService.loginUser(mockUser)).thenReturn(true);

        // mock userRepository user object return
        when(userRepository.findByUserName(username)).thenReturn(mockUser);
        // mock password input incorrect
        when(passwordEncoder.matches(wrongPw, mockUser.getPw())).thenReturn(false);
        // mock post request to login page to simulate incorrect login
        MvcResult result = mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("pw", wrongPw))
                .andExpect(status().is(401))
                //.andExpect(view().name("my-account"))
                //.andExpect(model().attributeExists("loginError"))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("login_failed", response, "Response message check");

    }

    @Test
    public void userNotExistingTest() throws Exception {
        String username = "mockUser";
        String password = "password";

        // mock userRepository to return false for checkUserExists simulating user not in db
        when(userRepository.checkUserExists(username)).thenReturn(false);
        // mock post request to login page and simulate user login with non-existing username
       MvcResult result = mockMvc.perform(post("/login")
                .param("username", username)
                .param("pw", password))
        .andExpect(status().is(401))
        //.andExpect(view().name("my-account"))
        //.andExpect(model().attributeExists("loginError"))
        .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("login_failed", response, "Response message check");
    }

*/
    @Test
    public void testRegistrationSuccess() throws Exception {
        String username = "mockUser";
        String correctPw = "mockPassword";
        String mockToken = tokenService.generateToken(mockUser);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + mockToken);


        // Mock userService to return true indicating successful login
        when(userService.registerUser(any(User.class))).thenReturn(true);
        when(userService.createTokenAndCookie(any(User.class), any(HttpServletResponse.class), anyString()))
                .thenReturn(ResponseEntity.ok().headers(header).body("registration_success"));

        // Perform the login test
        MvcResult result = mockMvc.perform(post("/register")
                        .param("username", username)
                        .param("pw", correctPw))
                .andExpect(status().isOk())
                .andReturn();

        // Extract token and response
        String token = result.getResponse().getHeader("Authorization");
        String response = result.getResponse().getContentAsString();

        // Assertions
        assertNotNull(token, "Token check");
        assertEquals("registration_success", response, "Response message check");


    }

    @Test
    public void testRegistrationFailure() throws Exception {
        String username = "mockUser";
        String correctPw = "mockPassword";


        // Mock userService to return true indicating successful login
        when(userService.registerUser(any(User.class))).thenReturn(false);


        // Perform the login test
        MvcResult result = mockMvc.perform(post("/register")
                        .param("username", username)
                        .param("pw", correctPw))
                .andExpect(status().is(401))
                .andReturn();

        // Extract token and response
        String response = result.getResponse().getContentAsString();

        assertEquals("registration_failed", response, "Response message check");


    }

}