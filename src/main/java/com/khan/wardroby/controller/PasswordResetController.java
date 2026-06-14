package com.khan.wardroby.controller;

import com.khan.wardroby.dto.ResetDto;
import com.khan.wardroby.exception.InvalidPasswordResetTokenException;
import com.khan.wardroby.exception.PasswordResetTokenExpiredException;
import com.khan.wardroby.exception.TokenUsedException;
import com.khan.wardroby.service.PasswordResetTokenService;
import com.khan.wardroby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {

    UserService service;
    PasswordResetTokenService tokenService;

    @Autowired
    PasswordResetController(UserService service, PasswordResetTokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }


    @GetMapping()
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        try{
            Optional<ResetDto> resetDto = tokenService.proccessToken(token);
            if(resetDto.isPresent())
            {
                model.addAttribute("token", resetDto.get());
                return "redirect:reset-password";
            }
            else{
                return "reset-password-failed";
            }
        }
        catch (InvalidPasswordResetTokenException e) {
            model.addAttribute("invalid-token", true);
        }
        return "reset-password-failed";
    }


    @PostMapping()
    public String processPasswordResetForm(@RequestParam("token") ResetDto token,
                                           @RequestParam("password") String password,
                                           @RequestParam("confirm-password") String confirmPassword,
                                           Model model) {
        try {
            if (token.getExpired()) {
                throw new PasswordResetTokenExpiredException("Token already expired try again");
            }
            if (token.getUsed()) {
                throw new TokenUsedException("Token already used.");
            }

            service.changePassword(token.getUserId(), password, confirmPassword);
        } catch (RuntimeException e){

        }


        return "reset-password";
    }


}
