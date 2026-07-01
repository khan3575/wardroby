package com.khan.wardroby.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PasswordResetTokenDTO {
    private Long userId;
    private String token;
    private Boolean used;
    private Boolean expired;
}
