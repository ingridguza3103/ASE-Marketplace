package com.example.Marketplace.repository;

import com.example.Marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}