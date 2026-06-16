package com.khan.wardroby.service;

import com.khan.wardroby.dao.AuthorityRepository;
import com.khan.wardroby.dao.UserRepository;
import com.khan.wardroby.dto.UserDTO;
import com.khan.wardroby.exception.*;
import com.khan.wardroby.mapper.UserMapper;
import com.khan.wardroby.model.Authority;
import com.khan.wardroby.model.PasswordResetToken;
import com.khan.wardroby.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthorityRepository authRepository;
    private final PasswordEncoder encoder;
    private final PasswordResetTokenService tokenService;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Autowired
    UserService(UserRepository userRepository, AuthorityRepository authRepository,
                PasswordEncoder encoder, PasswordResetTokenService tokenService,
                EmailService emailService, UserMapper userMapper
    )
    {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
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
        if(userDto.getPassword().equals(userDto.getConfirmPassword()))
        {
            throw new InvalidPasswordException("Invalid Email or Password");
        }
        if(userRepository.findByEmail(userDto.getEmail()).isPresent())
        {
            throw new UserAlreadyExistsException("There is an account with that email address: " + userDto.getEmail());
        }

        Users user = userMapper.toEntity(userDto);
        user.setPassword(encoder.encode(userDto.getPassword()));
        Authority auth = authRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new AuthorityNotFoundException("Default authority not found"));
        user.addAuthority(auth);

        System.out.print("successfully added a User");
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


    public void initiatePasswordReset(String email)
    {
        userRepository.findByEmail(email).ifPresent(user ->{
            String rawToken = tokenService.createAndSaveResetToken(user.getId());
            emailService.sendPasswordResetEmail(email, rawToken);
        });
    }

    @Transactional
    public void completePasswordReset(String rawToken, String password, String confirmPassword)
    {
        PasswordResetToken token = tokenService.verifyTokenForDisplay(rawToken);
        this.changePassword(token.getUserId(), password, confirmPassword);
        token.setUsed(true);
    }


}
