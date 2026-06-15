package com.khan.wardroby.controller;

import com.khan.wardroby.exception.UserNotFoundException;
import com.khan.wardroby.model.Users;
import com.khan.wardroby.service.EmailService;
import com.khan.wardroby.service.PasswordResetTokenService;
import com.khan.wardroby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forget-password")
public class RequestResetLinkController {
    private UserService service;
    private EmailService emailService;
    private PasswordResetTokenService tokenService;
    @Autowired
    public RequestResetLinkController(UserService service, EmailService emailService , PasswordResetTokenService tokenService)
    {
        this.service = service;
        this.emailService= emailService;
        this.tokenService = tokenService;
    }


    @GetMapping()
    public String showForgetPasswordFrom()
    {
        return "forgot-password";
    }

    @PostMapping()
    public String processFrom(@RequestParam("email") String email, Model model)
    {

        service.initiatePasswordReset(email);
        // Industry Standard: Always show success to prevent user enumeration
        model.addAttribute("submitted", true); //learn more about this part?

        return "forgot-password";
    }



}
