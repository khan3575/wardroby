package com.khan.wardroby.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min=3, max=12)
    private String password;

    @NotBlank
    @Size(min=3, max=12)
    private String confirmPassword;


    @NotBlank()
    @Size(min=1)
    private String firstName;

    @NotBlank()
    @Size(min=1)
    private String lastName;
}
