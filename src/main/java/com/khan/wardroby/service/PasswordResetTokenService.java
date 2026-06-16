package com.khan.wardroby.service;

import com.khan.wardroby.dao.PasswordResetTokenRepository;
import com.khan.wardroby.exception.InvalidPasswordResetTokenException;
import com.khan.wardroby.exception.ResetTokenException;
import com.khan.wardroby.exception.TokenUsedException;
import com.khan.wardroby.model.PasswordResetToken;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    private PasswordResetTokenRepository repository;


    @Autowired
    PasswordResetTokenService(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String createAndSaveResetToken(Long userId) {

        Optional<PasswordResetToken> existingToken = repository.findFirstByUserIdOrderByIdDesc(userId);
        if (existingToken.isPresent()) {
            // past 2 minute?
            PasswordResetToken token = existingToken.get();
            Instant tokenCreationTime = token.getExpiryDate().minus(15, ChronoUnit.MINUTES);
            if (tokenCreationTime.isAfter(Instant.now().minus(2, ChronoUnit.MINUTES)))
            {
                throw new ResetTokenException("Please wait 2 minute before trying again");
            }
        }
        return createToken(userId);
    }



    public String hashSha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm unavailable", e);
        }
    }


    @Transactional
    public String createToken(Long userId) {
        String rawToken = UUID.randomUUID().toString();
        String hashedToken = hashSha256(rawToken);


        PasswordResetToken passToken = new PasswordResetToken();
        passToken.setUserId(userId);
        passToken.setTokenHash(hashedToken);
        passToken.setExpiryDate(Instant.now().plus(15, ChronoUnit.MINUTES));
        passToken.setUsed(false);

        repository.save(passToken);
        return rawToken;
    }
    
    public PasswordResetToken verifyTokenForDisplay(String rawToken)
    {
        String hashedToken = hashSha256(rawToken);
        PasswordResetToken token = repository.findByTokenHash(hashedToken).orElseThrow(()-> new InvalidPasswordResetTokenException("Invalid Link"));

        if(token.getUsed()){
            throw new TokenUsedException("Linked already used");
        }
        if(token.getExpiryDate().isBefore(Instant.now()))
        {
            throw new InvalidPasswordResetTokenException("Link already expired");
        }
        return token;
    }





}

