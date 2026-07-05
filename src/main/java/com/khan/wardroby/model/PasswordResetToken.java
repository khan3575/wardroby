package com.khan.wardroby.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;


@Getter
@Setter
@ToString(exclude= "tokenHash")
@NoArgsConstructor
@Entity
@Table(name="password_reset_tokens")
public class PasswordResetToken {
    @Id

    @GeneratedValue( strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private Users user;

    @Column(name="token_hash")
    @NotNull
    private String tokenHash;

    @Column(name="expiry_date")
    private Instant expiryDate;

    @Column(name="used")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean used;
}
