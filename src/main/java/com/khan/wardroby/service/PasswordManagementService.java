package com.khan.wardroby.service;


import com.khan.wardroby.model.PasswordResetToken;
import com.khan.wardroby.model.Users;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordManagementService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordManagementService.class);

    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;

    @Autowired
    public PasswordManagementService(UserService userService, TokenService tokenService, EmailService emailService)
    {
        this.userService= userService;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Transactional
    public void initiateReset(String email)
    {
        userService.findByEmail(email).ifPresent(user ->{
            String rawToken = tokenService.createToken(user);
            emailService.sendPasswordResetEmail(email, rawToken);
            logger.info("Initiated password reset for user: {}", email);
        });
    }

    @Transactional
    public void completeReset(String rawToken, String newPassword ,String confirmPassword)
    {
        PasswordResetToken token = tokenService.verifyToken(rawToken);
        userService.changePassword(token.getUser().getId(), newPassword, confirmPassword);
        tokenService.markedAsUsed(token);
        logger.info("Successfully completed password reset for user ID: {}", token.getUser().getId());
    }

}

