package com.khan.wardroby.controller;

import com.khan.wardroby.dto.LoginDTO;
import com.khan.wardroby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {


    LoginController(){}

    @GetMapping()
    public String getLogin(Model model)
    {
        model.addAttribute("loginDto", new LoginDTO());
        return "login";
    }



}
