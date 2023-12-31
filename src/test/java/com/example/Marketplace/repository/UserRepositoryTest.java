package com.example.Marketplace.repository;

import com.example.Marketplace.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("user");
        user.setPw("test");
    }

    @AfterEach
    void tearDown() {
        user = null;
        // reset repository
        userRepository.deleteAll();
    }

    @Test
    void testCheckUserExists() {
        userRepository.save(user);

        assertTrue(userRepository.checkUserExists(user.getUsername()));

    }

    @Test
    void testCheckUserNotExists() {

        assertFalse(userRepository.checkUserExists(user.getUsername()));


    }

    @Test
    void testFindByUserName() {
        userRepository.save(user);

        User retrievedUser = userRepository.findByUserName(user.getUsername());

        assertEquals(user, retrievedUser, "User retrieved from database does not equal expected user");
    }

    @Test
    void testFindByUserNameNotExisting() {

        User retrievedUser = userRepository.findByUserName(user.getUsername());

        assertNull(retrievedUser);
    }

}