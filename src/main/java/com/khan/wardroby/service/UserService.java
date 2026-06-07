package com.khan.wardroby.service;

import com.khan.wardroby.dao.AuthorityRepository;
import com.khan.wardroby.dao.UserRepository;
import com.khan.wardroby.dto.UserDto;
import com.khan.wardroby.model.Authority;
import com.khan.wardroby.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private AuthorityRepository authRepository;
    private PasswordEncoder encoder;
    UserService(){}
    @Autowired
    UserService(UserRepository userRepository, AuthorityRepository authRepository, PasswordEncoder encoder)
    {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found in database"));
    }


    @Transactional
    public void registerNewUser(UserDto userDto)
    {
        Users user = new Users();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(encoder.encode(userDto.getPassword()));

        // Example of adding a default role
        // Assuming your Authority model setup
        Authority auth =  authRepository.findByAuthority("ROLE_USER").orElseThrow(()-> new RuntimeException("Couldnt fetch authority"));
        user.addAuthority(auth);
        userRepository.save(user);
        System.out.print("successfully added a User");
    }

}
