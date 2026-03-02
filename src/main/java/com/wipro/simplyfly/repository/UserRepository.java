package com.wipro.simplyfly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (for login)
    Optional<User> findByEmail(String email);

    // Find users by role (ADMIN / OWNER / USER)
    List<User> findByRole(String role);

    // Find only enabled users
    List<User> findByEnabledTrue();

}