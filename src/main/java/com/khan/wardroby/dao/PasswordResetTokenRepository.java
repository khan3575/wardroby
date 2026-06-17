package com.khan.wardroby.dao;

import com.khan.wardroby.model.PasswordResetToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    public Optional<PasswordResetToken> findByUser_Id(Long userId);
    public Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    public Optional<PasswordResetToken> findFirstByUser_IdOrderByIdDesc(Long userId);
}
