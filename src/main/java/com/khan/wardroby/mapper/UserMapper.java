package com.khan.wardroby.mapper;

import com.khan.wardroby.dto.UserDTO;
import com.khan.wardroby.model.Authority;
import com.khan.wardroby.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /* if specifically add data
    * @Mapping(source = "name", target ="fullName")
    * if entity has address (obj) and then need a value of it
    * @Mapping(source ="address.street" target="streetName")
    *  */
    @Mapping(target="password", ignore=true)
    @Mapping(target="confirmPassword", ignore=true)
    UserDTO toDto(Users user);


    @Mapping(target="id", ignore=true)
    @Mapping(target="enabled" , ignore=true)
    @Mapping(target="authorities", ignore=true)
    @Mapping(target="password" , ignore=true)
    Users toEntity(UserDTO userDTO);

}
