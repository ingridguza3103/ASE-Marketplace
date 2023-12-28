package com.example.Marketplace.repository;

import com.example.Marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository class for User objects, providing methods for Querying the database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Method querying the users table for a given username
     * @param username the given username
     * @return true if user exists in db false otherwise
     */
    @Query("SELECT EXISTS(SELECT u FROM User u WHERE u.username = :username)")
    boolean checkUserExists(String username);

    /**
     * Method querying the users table for a given username
     * @param username the given username
     * @return the User object corresponding to the username
     *         (null otherwise but should never be the case)
     */
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUserName(String username);
}