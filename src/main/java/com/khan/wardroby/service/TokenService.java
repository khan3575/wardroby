package com.khan.wardroby.service;

import com.khan.wardroby.dao.PasswordResetTokenRepository;
import com.khan.wardroby.exception.InvalidPasswordResetTokenException;
import com.khan.wardroby.exception.TokenUsedException;
import com.khan.wardroby.mapper.PasswordResetTokenMapper;
import com.khan.wardroby.model.PasswordResetToken;
import com.khan.wardroby.model.Users;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final PasswordResetTokenRepository repository;
    private final PasswordResetTokenMapper mapper;

    public TokenService(PasswordResetTokenRepository repository, PasswordResetTokenMapper mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public String createToken(Users user)
    {
        String rawToken = UUID.randomUUID().toString();
        String hashedToken = hashSha256(rawToken);


        PasswordResetToken passToken = mapper.toEntity(user, hashedToken);
        passToken.setExpiryDate(Instant.now().plus(15, ChronoUnit.MINUTES));

        repository.save(passToken);
        logger.info("Created reset token for user: {}", user.getEmail());
        return rawToken;
    }
    public PasswordResetToken verifyToken(String rawToken)
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
    public void markedAsUsed(PasswordResetToken token)
    {
        token.setUsed(true);
        repository.save(token);
    }

}
