package com.khan.wardroby.controller;

import com.khan.wardroby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {
    private UserService service;
    @Autowired
    public PasswordResetController(UserService service)
    {
        this.service = service;
    }


    @GetMapping("/forget-password")
    public String showForgetPasswordFrom()
    {
        return "forget-password";
    }

    @PostMapping("/forget-password")
    public String processFrom(@RequestParam("email") String email, Model model)
    {
        if(service.isUserExists(email))
        {
            // do email tasks
            System.out.println("Processing password reset for: " + email);
        }
        
        // Industry Standard: Always show success to prevent user enumeration
        model.addAttribute("submitted", true);

        return "forget-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword()
    {
        return "reset-password";
    }


}
