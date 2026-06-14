package com.khan.wardroby.service;

import com.khan.wardroby.dao.PasswordResetTokenRepository;
import com.khan.wardroby.dao.UserRepository;
import com.khan.wardroby.dto.ResetDto;
import com.khan.wardroby.exception.InvalidPasswordResetTokenException;
import com.khan.wardroby.model.PasswordResetToken;
import com.khan.wardroby.model.Users;
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
    private UserRepository userRepo;
    private PasswordResetTokenRepository repository;


    @Autowired
    PasswordResetTokenService(PasswordResetTokenRepository repository, UserRepository userRepo) {
        this.repository = repository;
        this.userRepo = userRepo;
    }

    @Transactional
    public String createAndSaveResetToken(String email) {
        Optional<Users> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        String rawToken = "";
        Optional<PasswordResetToken> existingToken = repository.findFirstByUserIdOrderByIdDesc(user.get().getId());
        if (existingToken.isPresent()) {
            // past 2 minute?
            PasswordResetToken token = existingToken.get();
            Instant tokenCreationTime = token.getExpiryDate().minus(15, ChronoUnit.MINUTES);
            if (tokenCreationTime.isBefore(Instant.now().minus(2, ChronoUnit.MINUTES))) {
                rawToken = createToken(user);
            } else {
                throw new RuntimeException("Please wait 2 minute before requesting another reset link.");
            }
        }


        return createToken(user);
    }


    public Optional<PasswordResetToken> verifyResetToken(String rawToken) {
        String hashedToken = hashSha256(rawToken);

        Optional<PasswordResetToken> tokenOptional = repository.findByTokenHash(hashedToken);

        if (tokenOptional.isEmpty()) {
            return Optional.empty();
        }
        PasswordResetToken token = tokenOptional.get();

        if (token.getUsed() || token.getExpiryDate().isBefore(Instant.now())) {
            return Optional.empty();
        }
        return Optional.of(token);
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
    public String createToken(Optional<Users> user) {
        String rawToken = UUID.randomUUID().toString();
        String hashedToken = hashSha256(rawToken);


        PasswordResetToken passToken = new PasswordResetToken();
        passToken.setUserId(user.get().getId());
        passToken.setTokenHash(hashedToken);
        passToken.setExpiryDate(Instant.now().plus(15, ChronoUnit.MINUTES));
        passToken.setUsed(false);

        repository.save(passToken);
        return rawToken;
    }

    @Transactional
    public Optional<ResetDto> proccessToken(String token)
    {
        // if token exists
        String hashedToken = hashSha256(token);
        Optional<PasswordResetToken> tokenOptional= repository.findByTokenHash(hashedToken);
        if(tokenOptional.isEmpty())
        {
            throw new InvalidPasswordResetTokenException("Token not found in DATABASE");
        }
        ResetDto resetDto = new ResetDto();

        PasswordResetToken resetToken = tokenOptional.get();
        Instant.now().isBefore(resetToken.getExpiryDate());
        resetDto.setUserId(resetToken.getUserId());
        resetDto.setTokenHash(resetToken.getTokenHash());
        if(!resetToken.getUsed())
        {
            //if not used then use it.
            resetToken.setUsed(true);
            repository.save(resetToken);
        }
        resetDto.setUsed(true);
        resetDto.setExpired(resetToken.getExpiryDate().isBefore(Instant.now()));

        return Optional.of(resetDto);
    }



}

