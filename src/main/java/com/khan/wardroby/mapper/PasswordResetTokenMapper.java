package com.khan.wardroby.mapper;

import com.khan.wardroby.dto.PasswordResetTokenDTO;
import com.khan.wardroby.model.PasswordResetToken;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PasswordResetTokenMapper {


    PasswordResetToken toEntity(PasswordResetTokenDTO dto);

    PasswordResetTokenDTO toDto(PasswordResetToken entity);
}
