package com.khan.wardroby.service;

import com.khan.wardroby.dao.AuthorityRepository;
import com.khan.wardroby.dao.UserRepository;
import com.khan.wardroby.dto.UserDto;
import com.khan.wardroby.exception.DatabaseException;
import com.khan.wardroby.exception.InvalidPasswordException;
import com.khan.wardroby.exception.UserAlreadyExistsException;
import com.khan.wardroby.exception.UserNotFoundException;
import com.khan.wardroby.model.Authority;
import com.khan.wardroby.model.PasswordResetToken;
import com.khan.wardroby.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private AuthorityRepository authRepository;
    private PasswordEncoder encoder;
    private PasswordResetTokenService tokenService;
    private EmailService emailService;
    UserService(){}
    @Autowired
    UserService(UserRepository userRepository, AuthorityRepository authRepository,
                PasswordEncoder encoder, PasswordResetTokenService tokenService,
                EmailService emailService
    )
    {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found in database"));
    }


    @Transactional
    public void registerNewUser(UserDto userDto)
    {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent())
        {
            throw new UserAlreadyExistsException("There is an account with that email address: " + userDto.getEmail());
        }

        Users user = new Users();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(encoder.encode(userDto.getPassword()));

        // Example of adding a default role
        // Assuming your Authority model setup
        Authority auth =  authRepository.findByAuthority("ROLE_USER").orElseThrow(()-> new DatabaseException("Couldn't fetch authority from database"));
        user.addAuthority(auth);
        userRepository.save(user);
        System.out.print("successfully added a User");
    }

    public Boolean isUserExists(String email)
    {
        return userRepository.findByEmail(email).isPresent();
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
               new UserNotFoundException("User couldnt found by id : "+userId));


        user.setPassword(encoder.encode(password));
        userRepository.save(user);
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
