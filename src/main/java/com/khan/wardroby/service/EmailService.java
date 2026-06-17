package com.khan.wardroby.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Autowired
    public  EmailService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    //async is for not making email wait.
    @Async
    public void sendPasswordResetEmail(String userEmail, String token)
    {
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        // 2. Build the email payload
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sakibkhan.stud@gmail.com"); // Matches the email you configured
        message.setTo(userEmail);
        message.setSubject("Password Reset Request");
        message.setText("You requested a password reset. \n\n" +
                "Click the link below to change your password:\n" +
                resetUrl + "\n\n" +
                "If you did not request this, please ignore this email. This link expires in 15 minutes.");

        // 3. Send the email
        mailSender.send(message);

        logger.info("Reset email successfully sent to: {}", userEmail);
    }




}
