package com.khan.wardroby.service;

import com.khan.wardroby.dao.AuthorityRepository;
import com.khan.wardroby.dao.UserRepository;
import com.khan.wardroby.dto.UserDTO;
import com.khan.wardroby.exception.*;
import com.khan.wardroby.mapper.UserMapper;
import com.khan.wardroby.model.Authority;
import com.khan.wardroby.model.Users;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AuthorityRepository authRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Autowired
    UserService(UserRepository userRepository, AuthorityRepository authRepository,
                PasswordEncoder encoder,
                UserMapper userMapper
    )
    {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found in database"));
    }


    @Transactional
    public void registerNewUser(UserDTO userDto)
    {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword()))
        {
            throw new InvalidPasswordException("Invalid Email or Password");
        }
        if(userRepository.existsByEmail(userDto.getEmail()))
        {
            throw new UserAlreadyExistsException("There is an account with that email address: " + userDto.getEmail());
        }

        Users user = userMapper.toEntity(userDto);
        user.setPassword(encoder.encode(userDto.getPassword()));
        Authority auth = authRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new AuthorityNotFoundException("Default authority not found"));
        user.addAuthority(auth);

        userRepository.save(user);
        logger.info("Successfully registered new user: {}", user.getEmail());
    }

    public Optional<Users> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }


    @Transactional
    public void changePassword(Long userId, String password, String confirmPassword)
    {

        if(password == null || !password.equals(confirmPassword))
        {
            throw new InvalidPasswordException("Password do not match or are empty");
        }
       Users user= userRepository.findById(userId).orElseThrow(()->
               new UserNotFoundException("User couldn't found by id : "+userId));


        user.setPassword(encoder.encode(password));
    }



    public void updatePassword(Long userId, String encodedPassword)
    {
        Users user= userRepository.findById(userId).orElseThrow(()->
                new UserNotFoundException("User couldn't found by id : "+userId));
        user.setPassword(encodedPassword);
    }


}
