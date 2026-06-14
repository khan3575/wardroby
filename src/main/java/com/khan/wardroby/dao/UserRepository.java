package com.khan.wardroby.dao;

import com.khan.wardroby.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findByEmail(String email);
    public Optional<Users> findById(Long id);
}
