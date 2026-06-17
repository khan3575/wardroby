package com.khan.wardroby.controller;

import com.khan.wardroby.service.PasswordManagementService;
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
    private final PasswordManagementService service;

    @Autowired
    PasswordResetController(PasswordManagementService service) {
        this.service = service;
    }


    @GetMapping()
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
       // We only need to pass the token to the form. 
       // The form will then POST it back for verification and completion.
       model.addAttribute("token",token);
       return "reset-password";
    }


    @PostMapping()
    public String processPasswordResetForm(@RequestParam("token") String token,
                                           @RequestParam("password") String password,
                                           @RequestParam("confirm-password") String confirmPassword,
                                           Model model) {
        service.completeReset(token, password, confirmPassword);
        return "redirect:login?resetSuccess";
    }


}
