package com.example.app;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsernameAndPassword(String username, String password);

    Admin findByUsername(String username);

    boolean existsByUsername(String username);
    
    Optional<Admin> findById(Long id);
}
