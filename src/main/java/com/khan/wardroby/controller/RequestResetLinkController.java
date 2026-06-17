package com.khan.wardroby.controller;

import com.khan.wardroby.service.PasswordManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forget-password")
public class RequestResetLinkController {
    private final PasswordManagementService passwordManagementService;

    @Autowired
    public RequestResetLinkController(PasswordManagementService passwordManagementService)
    {
        this.passwordManagementService = passwordManagementService;
    }


    @GetMapping()
    public String showForgetPasswordFrom()
    {
        return "forgot-password";
    }

    @PostMapping()
    public String processFrom(@RequestParam("email") String email, Model model)
    {
        passwordManagementService.initiateReset(email);
        // Industry Standard: Always show success to prevent user enumeration
        model.addAttribute("submitted", true); 

        return "forgot-password";
    }



}
