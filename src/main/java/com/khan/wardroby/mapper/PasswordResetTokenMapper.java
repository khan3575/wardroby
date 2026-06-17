package com.khan.wardroby.mapper;

import com.khan.wardroby.dto.PasswordResetTokenDTO;
import com.khan.wardroby.model.PasswordResetToken;
import com.khan.wardroby.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface PasswordResetTokenMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tokenHash", source = "hashedToken")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "used", constant = "false")
    @Mapping(target = "expiryDate", ignore = true) // Set dynamically in Service
    PasswordResetToken toEntity(Users user, String hashedToken);

    @Mapping(source = "user.id", target = "userId")
    PasswordResetTokenDTO toDto(PasswordResetToken entity);
}
