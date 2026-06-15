package com.khan.wardroby.controller;

import com.khan.wardroby.service.PasswordResetTokenService;
import com.khan.wardroby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
       tokenService.verifyTokenForDisplay(token);
       model.addAttribute("token",token);
       return "reset-password";
    }


    @PostMapping()
    public String processPasswordResetForm(@RequestParam("token") String token,
                                           @RequestParam("password") String password,
                                           @RequestParam("confirm-password") String confirmPassword,
                                           Model model) {
        service.completePasswordReset(token, password, confirmPassword);
        return "redirect:login?resetSuccess";
    }


}
