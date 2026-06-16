package com.khan.wardroby.controller;

import com.khan.wardroby.dto.UserDTO;
import com.khan.wardroby.exception.UserAlreadyExistsException;
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
@RequestMapping("/register")
public class RegistrationController {


    private UserService userService;

    @Autowired
    RegistrationController(UserService userService){
        this.userService=userService;
    }

    @GetMapping()
    public String showFrom(Model model)
    {
        model.addAttribute("user", new UserDTO());
        return "registration-page";
    }

    @PostMapping
    public String submitForm(@Valid @ModelAttribute("user") UserDTO userDto , BindingResult result)
    {
        System.out.println("userdto "+userDto);

        if(result.hasErrors())
        {
            return "registration-page";
        }
        if(!userDto.getPassword().equals(userDto.getConfirmPassword()))
        {
            result.rejectValue("password", "user.invalid-pass", "Password miss matched");
            return "registration-page";
        }
        try{
            userService.registerNewUser(userDto);
        }
        catch(UserAlreadyExistsException e)
        {
            System.out.println("Error : " + e.getMessage());
            result.rejectValue("email", "user.exits", e.getMessage());
            return "registration-page";
        }
        return "redirect:/login?registered";
    }


}
