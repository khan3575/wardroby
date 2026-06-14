package com.khan.wardroby.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(name="password_reset_tokens")
public class PasswordResetToken {
    @Id

    @GeneratedValue( strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_id")
    @NotNull
    private Long userId;

    @Column(name="token_hash")
    @NotNull
    private String tokenHash;

    @Column(name="expiry_date")
    private Instant expiryDate;

    @Column(name="used")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean used;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", user_id=" + userId +
                ", tokenHash='" + tokenHash + '\'' +
                ", expiryDate=" + expiryDate +
                ", used=" + used +
                '}';
    }
}
