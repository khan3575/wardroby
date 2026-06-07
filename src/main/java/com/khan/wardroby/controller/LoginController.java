package com.khan.wardroby.controller;

import com.khan.wardroby.dto.LoginDto;
import com.khan.wardroby.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    UserService service ;
    @Autowired
    LoginController(UserService service){
        this.service=service;
    }

    @GetMapping()
    public String getLogin(Model model)
    {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }



}
